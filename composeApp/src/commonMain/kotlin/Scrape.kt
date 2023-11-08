import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.HttpResponse
import io.ktor.http.parameters
import org.jsoup.Jsoup

private const val TAG = "Scraper"

data class Section (
    val name: String,
    val id: Int,
    val instructor: String,
    val location: String,
    val time: String,
    val location2: String,
    val time2: String,
    val count: String,
    val mode: String,
    val summerSession: String,
    val url: String,
    val status: String
)

suspend fun scrapeWebData(
    quantity: Int,
    term: String = "2238",
    reg_status: String = "all",
    subject: String = "",
    catalog_nbr_op: String = "=",
    catalog_nbr: String = "",
    title: String = "",
    instr_name_op: String = "=",
    instructor: String = "",
    ge: String = "",
    crse_units_op: String = "=",
    crse_units_from: String = "",
    crse_units_to: String = "",
    crse_units_exact: String = "",
    days: String = "",
    times: String = "",
    acad_career: String = "",
    asynch: String = "A",
    hybrid: String = "H",
    synch: String = "S",
    person: String = "P"
): List<Section> {

    val client = HttpClient(CIO)

    val response: HttpResponse = client.submitForm(
        url = "https://pisa.ucsc.edu/class_search/",
        // configure response parameters
        formParameters = parameters {
            append("action", "results")
            append("binds[:term]", term)
            append("binds[:reg_status]", reg_status)
            append("binds[:subject]", subject)
            append("binds[:catalog_nbr_op]", catalog_nbr_op)
            append("binds[:catalog_nbr]", catalog_nbr)
            append("binds[:title]", title)
            append("binds[:instr_name_op]", instr_name_op)
            append("binds[:instructor]", instructor)
            append("binds[:ge]", ge)
            append("binds[:crse_units_op]", crse_units_op)
            append("binds[:crse_units_from]", crse_units_from)
            append("binds[:crse_units_to]", crse_units_to)
            append("binds[:crse_units_exact]", crse_units_exact)
            append("binds[:days]", days)
            append("binds[:times]", times)
            append("binds[:acad_career]", acad_career)
            append("binds[:asynch]", asynch)
            append("binds[:hybrid]", hybrid)
            append("binds[:synch]", synch)
            append("binds[:person]", person)
            append("rec_start","0")
            append("rec_dur",quantity.toString())
        }
    )

    client.close()
    val responseBody: String = response.body()

    val document = Jsoup.parse(responseBody)

    val responseList = document.select(".panel.panel-default.row").map {
        val locations = it.select(".fa-location-arrow").size
        val summer = it.select(".fa-calendar").size != 0

        Section(
            name = it.select("a")[0].text(),
            id = it.select("div > a")[0].text().toIntOrNull() ?: 0,
            instructor = it.select(".col-xs-6:nth-child(2)")[0].text().split(": ")[1].replace(",", ", "),
            location = it.select(".col-xs-6:nth-child(1)")[1].text().split(": ", limit = 2)[1],
            time = it.select(".col-xs-6:nth-child(2)")[1].text().split(": ").getOrNull(1)?.trim() ?: "Not Found",
            location2 = if (locations > 1) it.select(".col-xs-6:nth-child(3)")[0].text().split(": ", limit = 2)[1] else "Not Found",
            time2 = if (locations > 1) it.select(".col-xs-6:nth-child(4)")[0].text().split(":").getOrNull(1)?.trim() ?: "Not Found" else "Not Found",
            count = it.select(".col-xs-6:nth-child(${if (summer) 5 else 4})")[locations - 1].text(),
            mode = it.select("b")[0].text(),
            summerSession = if (summer) it.select(".col-xs-6:nth-child(4)")[0].text().split(": ")[1] else "Not Found",
            url = it.select("a")[0].attr("href"),
            status = it.select("h2 .sr-only")[0].text()
        )
    }

    Logger.d(responseList.size.toString(), tag = TAG)
    for (section in responseList) {
        Logger.d(section.toString())
    }

    return responseList
}
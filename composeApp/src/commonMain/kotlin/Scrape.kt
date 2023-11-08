import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.HttpResponse
import io.ktor.http.parameters
import org.jsoup.Jsoup

const val TAG = "Scraper"

data class Listing (
    val name: String,
    val id: Int,
    val instructor: String,
    val location: String,
    val time: String,
    val url: String,
    val status: String
)

suspend fun scrapeWebData(
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
): List<Listing> {

    val responseList = mutableListOf<Listing>()

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
        }
    )

    client.close()
    val responseBody: String = response.body()

    val document = Jsoup.parse(responseBody)

    val classList = document.select("div.panel.panel-default.row")
    val classListII = document.select("div.row")
    val classListIII = document.select("div.panel.panel-default.row + div.row")

    Logger.d(classList.toString(), tag = TAG)
    Logger.d(classList.size.toString(), tag = TAG)
    Logger.d(classListII.size.toString(), tag = TAG)
    Logger.d(classListIII.size.toString(), tag = TAG)

    //todo make proper
    return responseList
}
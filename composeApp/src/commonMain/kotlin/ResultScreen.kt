
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import co.touchlab.kermit.Logger
import ui.SectionCard

private const val TAG = "ResultsScreen"
const val QUANTITY = 100
data class ResultsScreen(
    val input: String,
    val open: Boolean,
    val term: String = "2238",
    val asynch: String = "A",
    val hybrid: String = "H",
    val synch: String = "S",
    val person: String = "P"
): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val uriHandler = LocalUriHandler.current


        Logger.d(asynch+hybrid+synch+person+term, tag = TAG)

        var dataLoadedState by remember { mutableStateOf(false) }
        var classList by remember { mutableStateOf(listOf<Section>()) }

        val subjectList = setOf("APLX", "AM", "ARBC", "ART", "ARTG", "ASTR", "BIOC", "BIOL", "BIOE", "BME", "CRSN", "CHEM", "CHIN", "CSP", "CLNI", "CMMU", "CMPM", "CSE", "COWL", "CRES", "CRWN", "DANM", "EART", "ECON", "EDUC", "ECE", "ESCI", "ENVS", "FMST", "FILM", "FREN", "GAME", "GERM", "GCH", "GRAD", "GREE", "HEBR", "HIS", "HAVC", "HISC", "HCI", "HUMN", "ITAL", "JAPN", "JRLC", "KRSG", "LAAD", "LATN", "LALS", "LGST", "LING", "LIT", "MATH", "MERR", "METX", "MUSC", "NLP", "OAKS", "OCEA", "PERS", "PHIL", "PBS", "PHYE", "PHYS", "POLI", "PRTR", "PORT", "PSYC", "SCIC", "SOCD", "SOCY", "SPAN", "SPHS", "STAT", "STEV", "TIM", "THEA", "UCDC", "VAST", "WRIT")



        Logger.d(input.uppercase(),tag = TAG)
        LaunchedEffect(Unit) {

            val courseRegex = Regex("^\\d[a-zA-Z]?$|^\\d{2}[a-zA-Z]?$|^\\d{3}[a-zA-Z]?$")

            val isSubject = subjectList.contains(input.uppercase())
            val isCode = input.matches(courseRegex)

            val isBoth = (input.length <= 8 && subjectList.contains(
                input.uppercase().substringBefore(" ")
            ) && input.substringAfter(" ")
                .matches(courseRegex))

            try {
                classList = if (isBoth) {
                    scrapeWebData(
                        quantity = QUANTITY,
                        term = term,
                        reg_status = if (open) { "O" } else { "all" },
                        subject = input.uppercase().substringBefore(" "),
                        catalog_nbr = input.substringAfter(" "),
                        asynch = asynch,
                        hybrid = hybrid,
                        synch = synch,
                        person = person
                    )
                } else if (isSubject) {
                    scrapeWebData(
                        quantity = QUANTITY,
                        term = term,
                        reg_status = if (open) { "O" } else { "all" },
                        subject = input.uppercase(),
                        asynch = asynch,
                        hybrid = hybrid,
                        synch = synch,
                        person = person
                    )
                } else if (isCode) {
                    scrapeWebData(
                        quantity = QUANTITY,
                        term = term,
                        reg_status = if (open) { "O" } else { "all" },
                        catalog_nbr = input.uppercase(),
                        asynch = asynch,
                        hybrid = hybrid,
                        synch = synch,
                        person = person
                    )
                } else {
                    scrapeWebData(
                        quantity = QUANTITY,
                        term = term,
                        reg_status = if (open) { "O" } else { "all" },
                        title = input,
                        asynch = asynch,
                        hybrid = hybrid,
                        synch = synch,
                        person = person
                    )
                }
            } catch (e: Exception) {
                Logger.d("Exception caught: $e", tag = TAG)
            }
            dataLoadedState = true
        }

        Box (modifier = Modifier.padding(horizontal = 8.dp).fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            if (dataLoadedState) {
                if (classList.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.widthIn(max=800.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        item {
                            Box(Modifier.fillMaxWidth().align(Alignment.CenterStart)) {
                                Button(
                                    onClick = { navigator.pop() },
                                    content = { Text("Go back") },
                                )
                            }
                        }
                        items(classList.size) {
                            SectionCard(classList[it], uriHandler)
                        }
                    }
                } else {
                    Column {
                        Text(
                            text = "No classes found for your search.",
                            fontSize = 24.sp
                        )
                        Button(onClick = { navigator.pop() }, content = { Text("Go back") })
                    }

                }
            } else {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
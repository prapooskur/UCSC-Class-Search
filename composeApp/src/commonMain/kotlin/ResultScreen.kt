
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import co.touchlab.kermit.Logger
import ui.SectionCard

private const val TAG = "ResultsScreen"
const val QUANTITY = 25
data class ResultsScreen(val input: String): Screen {
    @Composable
    override fun Content() {
        var dataLoadedState by remember { mutableStateOf(false) }
        var classList by remember { mutableStateOf(listOf<Section>()) }

        val subjectList = setOf("APLX", "AM", "ARBC", "ART", "ARTG", "ASTR", "BIOC", "BIOL", "BIOE", "BME", "CRSN", "CHEM", "CHIN", "CSP", "CLNI", "CMMU", "CMPM", "CSE", "COWL", "CRES", "CRWN", "DANM", "EART", "ECON", "EDUC", "ECE", "ESCI", "ENVS", "FMST", "FILM", "FREN", "GAME", "GERM", "GCH", "GRAD", "GREE", "HEBR", "HIS", "HAVC", "HISC", "HCI", "HUMN", "ITAL", "JAPN", "JRLC", "KRSG", "LAAD", "LATN", "LALS", "LGST", "LING", "LIT", "MATH", "MERR", "METX", "MUSC", "NLP", "OAKS", "OCEA", "PERS", "PHIL", "PBS", "PHYE", "PHYS", "POLI", "PRTR", "PORT", "PSYC", "SCIC", "SOCD", "SOCY", "SPAN", "SPHS", "STAT", "STEV", "TIM", "THEA", "UCDC", "VAST", "WRIT")


        Logger.d(input.uppercase(),tag = TAG)
        LaunchedEffect(Unit) {
            try {
                classList = if (subjectList.contains(input.uppercase())) {
                    scrapeWebData(quantity = QUANTITY, subject = input.uppercase())
                } else if (input.matches(Regex("^\\d$|^\\d{2}[a-zA-Z]?$|^\\d{3}[a-zA-Z]?$"))) {
                    scrapeWebData(quantity = QUANTITY, catalog_nbr = input.uppercase())
                } else {
                    scrapeWebData(quantity = QUANTITY, title = input)
                }

            } catch (e: Exception) {
                Logger.d("Exception caught: $e", tag = TAG)
            }
            dataLoadedState = true
        }

        Box (modifier = Modifier.padding(8.dp).fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            if (dataLoadedState) {
                if (classList.isNotEmpty()) {
                    LazyColumn() {
                        items(classList.size) {
                            SectionCard(classList[it])
                        }
                    }
                } else {
                    Text(
                        text = "No classes found for your search.",
                        fontSize = 24.sp
                    )
                }
            } else {
                Box() {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen


data class ResultsScreen(val title: String): Screen {
    @Composable
    override fun Content() {
        var dataLoadedState by remember { mutableStateOf(false) }
        var classList by remember { mutableStateOf(listOf<Section>()) }

        LaunchedEffect(Unit) {
            classList = scrapeWebData(quantity = 25, title = title)
            dataLoadedState = true
        }

        if (dataLoadedState) {
            LazyColumn(Modifier.padding(8.dp)) {
                items(classList.size) {
                    Text(
                        text = classList[it].name,
                    )
                }
            }
        } else {
            Box(Modifier.padding(8.dp)) {
                CircularProgressIndicator()
            }
        }
    }
}
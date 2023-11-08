
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {

    var dataLoadedState by remember { mutableStateOf(false) }
    var classList by remember { mutableStateOf(listOf<Section>()) }
    LaunchedEffect(Unit) {
        classList = scrapeWebData(quantity = 24)
        dataLoadedState = true
    }
    MaterialTheme {
        var searchText by rememberSaveable { mutableStateOf("") }
        var searchActive by rememberSaveable { mutableStateOf(false) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text =  "UCSC Class Search",
                fontWeight = FontWeight.SemiBold,
                fontSize = 40.sp
            )

            SearchBar(
                query = searchText,
                onQueryChange = { searchText = it },
                active = false,
                onActiveChange = { searchActive = it },
                onSearch = { searchActive = false },
                placeholder = { Text("Search for classes") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search icon") },
            ) {
                repeat(4) { idx ->
                    val resultText = "Suggestion $idx"
                    ListItem(
                        headlineContent = { Text(resultText) },
                        supportingContent = { Text("Additional info") },
                        leadingContent = { Icon(Icons.Filled.Search, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }

            }

            if (dataLoadedState) {
                LazyColumn {
                    items(classList.size) {
                        Text(
                            text = classList[it].name,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}
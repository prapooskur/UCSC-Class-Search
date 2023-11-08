
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class HomeScreen: Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        MaterialTheme {
            var searchText by rememberSaveable { mutableStateOf("") }
            var searchActive by rememberSaveable { mutableStateOf(false) }

            val navigator = LocalNavigator.currentOrThrow

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
                    onSearch = { navigator.push(ResultsScreen(searchText)) },
                    placeholder = { Text("Search for classes") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search icon") },
                ) {}


            }
        }
    }

}
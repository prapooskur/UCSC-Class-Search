
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class HomeScreen: Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var searchText by rememberSaveable { mutableStateOf("") }
        var searchActive by rememberSaveable { mutableStateOf(false) }

        val searchAsyncOnline = rememberSaveable { mutableStateOf(true) }
        val searchHybrid = rememberSaveable { mutableStateOf(true) }
        val searchSynchOnline = rememberSaveable { mutableStateOf(true) }
        val searchInPerson = rememberSaveable { mutableStateOf(true) }

        val searchOpen = rememberSaveable { mutableStateOf(false) }

        val navigator = LocalNavigator.currentOrThrow

        val termChosen = rememberSaveable { mutableStateOf("Winter 2024") }
        val geChosen = rememberSaveable { mutableStateOf("") }

        val termMap = mapOf(
            "Winter 2024" to "2240",
            "Fall 2023" to "2238",
            "Spring 2023" to "2232",
            "Winter 2023" to "2230",
            "Fall 2022" to "2228"
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize().offset(y = (-25).dp)) {

            Text(
                text =  "UCSC Class Search",
                fontWeight = FontWeight.SemiBold,
                fontSize = 40.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            SearchBar(
                query = searchText,
                onQueryChange = { searchText = it },
                active = false,
                onActiveChange = { searchActive = it },
                onSearch = {
                    navigator.push(
                        ResultsScreen(
                            input = searchText,
                            open = searchOpen.value,
                            term = termMap[termChosen.value] ?: "2238",
                            ge = geChosen.value,
                            asynch = if (searchAsyncOnline.value) "A" else "",
                            hybrid = if (searchHybrid.value) "H" else "",
                            synch = if (searchSynchOnline.value) "S" else "",
                            person = if (searchInPerson.value) "P" else ""
                        )
                    )
                },
                placeholder = { Text("Search for classes") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search icon") },
                modifier = Modifier.padding(12.dp)
            ) { /* do nothing */ }

            Row(Modifier.widthIn(max = 350.dp)) {
                TermChooser(termChosen)
                GEChooser(geChosen)
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.offset(x=(-35).dp)) {
                Text("Status: ")

                RadioButton(selected = !searchOpen.value, onClick = { searchOpen.value = false })
                Text("All")

                RadioButton(selected = searchOpen.value, onClick = { searchOpen.value = true })
                Text("Open")

            }

            Column(modifier = Modifier.offset(x= (-72).dp)) {
                TimeCheckbox("Asynchronous", searchAsyncOnline)
                TimeCheckbox("Hybrid", searchHybrid)
                TimeCheckbox("Synchronous", searchSynchOnline)
                TimeCheckbox("In Person", searchInPerson)
            }








        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermChooser(termChosen: MutableState<String>) {

    val options = listOf("Winter 2024", "Fall 2023", "Spring 2023", "Winter 2023", "Fall 2022")
    var expanded by remember { mutableStateOf(false) }
// We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.widthIn(max=200.dp).padding(end=25.dp)
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = termChosen.value,
            onValueChange = {},
            label = { Text("Term") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        termChosen.value = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GEChooser(geChosen: MutableState<String>) {

    val options = listOf("","CC","ER","IM","MF","SI","SR","TA","PE-E","PE-H","PE-T","PR-E","PR-C","PR-S","C")
    var expanded by remember { mutableStateOf(false) }
// We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.widthIn(max=175.dp)
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = geChosen.value,
            onValueChange = {},
            label = { Text("GE") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        geChosen.value = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun TimeCheckbox(text: String, isChecked: MutableState<Boolean>) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color.Transparent
    ) {
        Row(
            Modifier
                //.fillMaxWidth()
                .height(40.dp)
                .toggleable(
                    value = isChecked.value,
                    onValueChange = { isChecked.value = !isChecked.value },
                    role = Role.Checkbox
                )
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Checkbox(
                checked = isChecked.value,
                //onCheckedChange = { isChecked.value = !isChecked.value }
                onCheckedChange = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = text)
        }
    }
}
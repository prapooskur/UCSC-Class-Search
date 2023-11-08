
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    Navigator(HomeScreen()) {navigator ->  
        FadeTransition(navigator)
    }
}

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    AppTheme(darkTheme = isSystemInDarkTheme()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.statusBarsPadding()) {
                Navigator(HomeScreen()) { navigator ->
                    FadeTransition(navigator)
                }
            }
        }
    }
}
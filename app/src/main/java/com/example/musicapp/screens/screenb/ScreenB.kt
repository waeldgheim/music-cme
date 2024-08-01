import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicapp.screens.screenb.ScreenBViewModel

@Composable
fun ScreenBContent(buttonId: String?) {
    val viewModel = hiltViewModel<ScreenBViewModel>()
    Text(
        text = "Button ID: $buttonId",
        modifier = Modifier
    )
}

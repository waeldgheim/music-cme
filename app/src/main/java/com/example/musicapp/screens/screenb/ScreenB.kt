import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScreenBContent(buttonId: String?) {
    Text(
        text = "Button ID: $buttonId",
        modifier = Modifier
    )
}

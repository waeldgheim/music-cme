package com.example.musicapp.Fragments.FragmentB

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicapp.ui.theme.MusicAppTheme


class FragmentB : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MusicAppTheme {
                    FragmentBContent()
                }
            }
        }
    }
}

@Composable
fun FragmentBContent() {
    Text(
        text = "Fragment B",
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun FragmentBContentPreview() {
    MusicAppTheme {
        FragmentBContent()
    }
}
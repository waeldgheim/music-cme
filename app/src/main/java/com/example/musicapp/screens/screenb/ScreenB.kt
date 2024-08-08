package com.example.musicapp.screens.screenb

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicapp.screens.screena.GlideImage

@Composable
fun ScreenBContent(albumId: String?) {
    val viewModel: ScreenBViewModel = hiltViewModel()
    albumId?.let {
        viewModel.getAlbumDetails(it)
    }

    val albumDetails by viewModel.albumDetails.collectAsState()

    albumDetails?.let { album ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            album.imageUrl?.let {
                GlideImage(
                    imageUrl = it,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = album.name, fontSize = 24.sp)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = album.artistName, fontSize = 18.sp)
            Spacer(modifier = Modifier.size(16.dp))
            album.genres.forEach { genre ->
                Text(
                    text = genre,
                    style = TextStyle(
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(vertical = 4.dp) // Add vertical spacing between items
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = album.releaseData, fontSize = 18.sp)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = album.copyright, fontSize = 18.sp)
            Spacer(modifier = Modifier.size(8.dp))
            album.url?.let { Text(text = it, fontSize = 18.sp) }

        }
    }
}
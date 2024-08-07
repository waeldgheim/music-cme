package com.example.musicapp.screens.screena

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicapp.Detail
import com.example.musicapp.database.DatabaseAlbum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun ScreenAContent(navController: NavController) {
val viewModel: ScreenAViewModel = hiltViewModel()
val albumList by viewModel.albums.collectAsState(initial = emptyList())
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(albumList) { album ->
            AlbumItem(album = album, navController)
        }
    }
}

@Composable
fun AlbumItem(album: DatabaseAlbum, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("${Detail.route}/${album.id}")
            }
    ) {
        album.imageUrl?.let {
            GlideImage(
                imageUrl = it,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = album.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = album.artistName, fontSize = 16.sp)
    }
}

@Composable
fun GlideImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    requestOptions: RequestOptions = RequestOptions()
) {
    val context = LocalContext.current
    val imageBitmap: ImageBitmap? = remember {
        runBlocking {
            loadImageBitmap(context, imageUrl, requestOptions)
        }
    }
    imageBitmap?.let { bitmap ->
        Image(
            painter = BitmapPainter(bitmap),
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}

suspend fun loadImageBitmap(
    context: android.content.Context,
    url: String,
    requestOptions: RequestOptions
): ImageBitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val bitmap = Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .submit()
                .get()
            bitmap.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

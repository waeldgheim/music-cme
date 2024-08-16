package com.example.musicapp.screens.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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

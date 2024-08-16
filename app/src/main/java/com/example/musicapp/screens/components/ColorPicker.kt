package com.example.musicapp.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.screens.screena.ScreenAViewModel
import com.example.musicapp.ui.theme.Theme
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ImageColorPicker
import com.github.skydoves.colorpicker.compose.PaletteContentScale
import com.github.skydoves.colorpicker.compose.rememberColorPickerController


@Composable
fun ColorPickerDialog(
    isDialogOpen: Boolean,
    onDismiss: () -> Unit,
    viewModel: ScreenAViewModel
) {
    var pickedColor by remember { mutableStateOf(Color.Transparent) }

    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Pick a Color",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            text = {
                ColorPicker(
                    onColorChanged = { color ->
                        pickedColor = color
                    }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        val colorLong = pickedColor.toArgb().toLong()
                        Theme.saveColor(pickedColor.toArgb().toLong())
                        viewModel.updateColor(colorLong)
                        onDismiss()
                    }
                ) {
                    Text(text = "OK", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(text = "Cancel", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        )
    }
}

@Composable
fun ColorPicker(
    onColorChanged: (Color) -> Unit
) {
    val controller = rememberColorPickerController()

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            AlphaTile(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(6.dp)),
                controller = controller
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            val widthInDp = maxWidth
            ImageColorPicker(
                modifier = Modifier
                    .width(widthInDp)
                    .height(widthInDp),
                controller = controller,
                paletteImageBitmap = ImageBitmap.imageResource(R.drawable.color_spectrum),
                paletteContentScale = PaletteContentScale.FIT,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    onColorChanged(colorEnvelope.color)
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(10.dp)
                .height(35.dp)
                .align(Alignment.CenterHorizontally),
            controller = controller,
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

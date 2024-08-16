package com.example.musicapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.musicapp.ui.theme.ColorUtils.darkenColor
import com.example.musicapp.ui.theme.ColorUtils.lightenColor

@Composable
fun MusicAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val color = Color(0xFF00008B)
    val LightColorPalette = lightColorScheme(
        primary = color,
        secondary = lightenColor(color, 0.3f),
        background = lightenColor(color, 0.9f),
        onPrimary = Color(0xFFFFFFFF),
        onSecondary = Color(0xFF808080),
        onBackground = Color(0xFF000000)
    )

    val DarkColorPalette = darkColorScheme(
        primary = color,
        secondary = darkenColor(color, 0.5f),
        background = darkenColor(color, 0.9f),
        onPrimary = Color(0xFFFFFFFF),
        onSecondary = Color(0xFF808080),
        onBackground = Color(0xFFFFFFFF)
    )

    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}

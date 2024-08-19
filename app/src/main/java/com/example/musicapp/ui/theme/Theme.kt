package com.example.musicapp.ui.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.musicapp.ui.theme.ColorUtils.darkenColor
import com.example.musicapp.ui.theme.ColorUtils.isLuminant
import com.example.musicapp.ui.theme.ColorUtils.lightenColor


object Theme {
    private var color: Long = 0xFF0C3C85
    private var preferences: SharedPreferences? = null

    fun initialize(context: Context) {
        preferences = context.getSharedPreferences("SETTINGS",
            Context.MODE_PRIVATE)
        color = preferences?.getLong("COLOR", 0xFF0C3C85) ?: 0xFF0C3C85
    }

    fun getTColor(): Color {
        return Color(this.color)
    }

    fun saveColor(color: Long){
        this.color = color
        preferences?.edit()?.putLong("COLOR", color)?.apply()
    }
}

@Composable
fun MusicAppTheme(
    color: Color,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val LightColorPalette = lightColorScheme(
        primary = color,
        secondary = lightenColor(color, 0.3f),
        background = lightenColor(color, 0.8f),
        onPrimary = if (isLuminant(color)) Color(0xFF000000) else  Color(0xFFFFFFFF),
        onSecondary = Color(0xFF808080),
        onBackground = Color(0xFF000000)
    )

    val DarkColorPalette = darkColorScheme(
        primary = color,
        secondary = darkenColor(color, 0.5f),
        background = darkenColor(color, 0.8f),
        onPrimary = if (isLuminant(color)) Color(0xFF000000) else  Color(0xFFFFFFFF),
        onSecondary = Color(0xFFA5A5A5),
        onBackground = Color(0xFFFFFFFF)
    )

    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}

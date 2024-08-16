package com.example.musicapp.ui.theme


import androidx.compose.ui.graphics.Color


object ColorUtils {
    fun lightenColor(color: Color, factor: Float = 0.7f): Color {
        val clampedFactor = factor.coerceIn(0f, 1f)
        return color.copy(
            red = (color.red + (1f - color.red) * clampedFactor).coerceIn(0f, 1f),
            green = (color.green + (1f - color.green) * clampedFactor).coerceIn(0f, 1f),
            blue = (color.blue + (1f - color.blue) * clampedFactor).coerceIn(0f, 1f)
        )
    }

    fun darkenColor(color: Color, factor: Float = 0.7f): Color {
        val clampedFactor = factor.coerceIn(0f, 1f)
        return color.copy(
            red = (color.red * (1f - clampedFactor)).coerceIn(0f, 1f),
            green = (color.green * (1f - clampedFactor)).coerceIn(0f, 1f),
            blue = (color.blue * (1f - clampedFactor)).coerceIn(0f, 1f)
        )
    }
}

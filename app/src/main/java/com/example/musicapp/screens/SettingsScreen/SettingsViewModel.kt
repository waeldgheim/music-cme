package com.example.musicapp.screens.SettingsScreen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.musicapp.ui.theme.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {
    private val _color = MutableStateFlow(Theme.getTColor())
    val color: StateFlow<Color> get() = _color

    fun updateColor(newColor: Long) {
        _color.value = Color(newColor)
    }
}

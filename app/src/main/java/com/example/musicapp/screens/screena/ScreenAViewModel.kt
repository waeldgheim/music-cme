package com.example.musicapp.screens.screena

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.repository.MusicRepository
import com.example.musicapp.ui.theme.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenAViewModel @Inject constructor (
    private val musicRepository: MusicRepository
): ViewModel() {
    private val _color = MutableStateFlow(Theme.getTColor())
    val color: StateFlow<Color> get() = _color

    fun updateColor(newColor: Long) {
        _color.value = Color(newColor)
    }

    val status = musicRepository.status
    val albums = musicRepository.albums

    init {
        viewModelScope.launch {
            musicRepository.refreshAlbums()
        }
    }

    fun refresh(){
        viewModelScope.launch {
            musicRepository.refreshAlbums()
        }
    }

}

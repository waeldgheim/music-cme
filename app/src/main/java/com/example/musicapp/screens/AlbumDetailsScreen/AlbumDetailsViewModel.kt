package com.example.musicapp.screens.AlbumDetailsScreen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.database.DatabaseAlbum
import com.example.musicapp.repository.MusicRepository
import com.example.musicapp.ui.theme.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val musicRepository: MusicRepository
): ViewModel() {
    private val _color = MutableStateFlow(Theme.getTColor())
    val color: StateFlow<Color> get() = _color

    fun updateColor(newColor: Long) {
        _color.value = Color(newColor)
    }

    private val _albumDetails = MutableStateFlow<DatabaseAlbum?>(null)
    val albumDetails: StateFlow<DatabaseAlbum?> = _albumDetails

    fun getAlbumDetails(id: String){
        viewModelScope.launch {
            try {
                musicRepository.getAlbumById(id)
                _albumDetails.value = musicRepository.albumDetails
            } catch (e:Exception){
                _albumDetails.value = null
            }
        }
    }
}

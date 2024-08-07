package com.example.musicapp.screens.screena

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenAViewModel @Inject constructor (
    private val musicRepository: MusicRepository
): ViewModel() {

    init {
        viewModelScope.launch {
            musicRepository.refreshAlbums()
        }
    }
    val albums = musicRepository.albums
}

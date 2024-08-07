package com.example.musicapp.screens.screenb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.database.DatabaseAlbum
import com.example.musicapp.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScreenBViewModel @Inject constructor(
    private val musicRepository: MusicRepository
): ViewModel() {

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


package com.example.musicapp.screens.screenb

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.realm.Album
import com.example.musicapp.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenBViewModel @Inject constructor(
    private val musicRepository: MusicRepository
): ViewModel() {

    private val _albumDetails = MutableStateFlow<Album?>(null)
    val albumDetails: StateFlow<Album?> = _albumDetails
    fun getAlbumDetails(id: String){
        viewModelScope.launch {
            try {
                Log.i("mytag", albumDetails.toString())

                musicRepository.getAlbumById(id)
                _albumDetails.value = musicRepository.albumDetails
            } catch (e:Exception){
                _albumDetails.value = null
            }
        }
    }
}

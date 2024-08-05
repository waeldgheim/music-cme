package com.example.musicapp.screens.screena

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.network.MusicAppService
import com.example.musicapp.network.NetworkAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenAViewModel @Inject constructor (
    private val musicAppService: MusicAppService
): ViewModel() {

    private val _albums = MutableStateFlow<List<NetworkAlbum>>(emptyList())
    val album: StateFlow<List<NetworkAlbum>>
        get() =_albums

    init {
        fetchAlbums()
    }

    private fun fetchAlbums(){
        viewModelScope.launch {
            try{
                _albums.value = musicAppService.getTopHundred().feed.results
            } catch (e: Exception){
                Log.e("Error",e.toString())
            }
        }
    }
}

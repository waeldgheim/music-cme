package com.example.musicapp.screens.screena

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenAViewModel @Inject constructor (
    private val repository: MusicRepository
): ViewModel() {
    val albums = repository.albums
    init {
        fetchAlbums()
    }
    private fun fetchAlbums(){
        viewModelScope.launch {
            try{
                repository.refreshAlbums()
            } catch (e: Exception){
                Log.e("Error",e.toString())
            }
        }
    }
}

package com.example.musicapp.screens.FilterScreen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.database.DatabaseAlbum
import com.example.musicapp.repository.MusicRepository
import com.example.musicapp.ui.theme.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    private val _color = MutableStateFlow(Theme.getTColor())
    val color: StateFlow<Color> get() = _color

    fun updateColor(newColor: Long) {
        _color.value = Color(newColor)
    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing

    val status = musicRepository.status

    val albums = musicRepository.albums

    private val _selectedGenre = MutableStateFlow("All")
    val selectedGenre: StateFlow<String> = _selectedGenre.asStateFlow()

    val allGenres: StateFlow<List<String>> = albums.map { albumsList ->
        albumsList.flatMap { it.genre.split(", ") }
            .distinct()
            .sorted()
    }.stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    val filteredAlbums: StateFlow<List<DatabaseAlbum>> =
        combine(albums, selectedGenre) { albumsList, selectedGenre ->
            if (selectedGenre == "All") albumsList
            else albumsList.filter { selectedGenre in it.genre.split(", ") }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun selectGenre(genre: String) {
        _selectedGenre.value = genre
    }

    fun refresh() {
        viewModelScope.launch {
            musicRepository.refreshAlbums()
        }
        _isRefreshing.value = false
    }
}

package com.example.musicapp.screens.screena

import androidx.lifecycle.ViewModel
import com.example.musicapp.network.MusicAppService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScreenAViewModel @Inject constructor (
    private val musicAppService: MusicAppService
): ViewModel() {
    val test = "From viewModel"
}

package com.example.musicapp

sealed interface MusicDestinations {
    val route: String
}

data object ButtonList: MusicDestinations{
    override val route = "list"
}

data object Detail: MusicDestinations{
    override val route = "detail"
}
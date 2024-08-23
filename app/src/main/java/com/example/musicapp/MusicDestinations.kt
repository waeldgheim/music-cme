package com.example.musicapp

sealed interface MusicDestinations {
    val route: String
}

data object Home : MusicDestinations {
    override val route = "home"
}

data object Detail : MusicDestinations {
    override val route = "detail"
}

data object Filter : MusicDestinations {
    override val route = "filter"
}

data object Settings : MusicDestinations {
    override val route = "settings"
}

package com.example.musicapp.domain

import java.util.Date

data class Album(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val artistName: String,
    val releaseDate: Date,
    val genres: List<Genre>
)

data class Genre(
    val id: Long,
    val name: String,
    val url: String
)
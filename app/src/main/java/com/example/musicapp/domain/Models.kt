package com.example.musicapp.domain

data class Album(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val artistName: String,
    val releaseDate: String,
    val genres: List<Genre>
)

data class Genre(
    val id: Long,
    val name: String,
    val url: String
)
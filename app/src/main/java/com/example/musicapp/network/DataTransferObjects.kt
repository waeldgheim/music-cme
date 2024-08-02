package com.example.musicapp.network

import com.squareup.moshi.Json

data class NetworkAlbumResponse(val feed: Feed)

data class Feed(
    val copyright: String,
    val results: List<NetworkAlbum>
)

data class NetworkAlbum(
    val artistName: String,
    val id: String,
    val name: String,
    val releaseDate: String,
    val kind: String,
    @Json(name = "artworkUrl100") val imageUrl: String?,
    val genres: List<Genre>,
    val url: String?
)

data class Genre(
    val genreId: String,
    val name: String,
    val url: String
)

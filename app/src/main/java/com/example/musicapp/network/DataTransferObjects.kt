package com.example.musicapp.network

import com.example.musicapp.realm.Album
import com.squareup.moshi.Json
import io.realm.kotlin.ext.realmListOf

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
    @Json(name = "artworkUrl100") val imageUrl: String,
    val genres: List<Genre>,
    val url: String
)

data class Genre(
    val name: String,
)

fun NetworkAlbumResponse.asRealmModel(): List<Album> {
    return feed.results.map {
        Album().apply {
            id = it.id
            name = it.name
            artistName = it.artistName
            genres = realmListOf(*it.genres.map { genre -> genre.name }.toTypedArray())
            imageUrl = it.imageUrl
            releaseDate = it.releaseDate
            copyright = feed.copyright
            url = it.url
        }
    }
}


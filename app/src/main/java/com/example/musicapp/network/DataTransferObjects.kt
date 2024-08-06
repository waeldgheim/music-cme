package com.example.musicapp.network

import com.example.musicapp.database.DatabaseAlbum
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
    @Json(name = "artworkUrl100") val imageUrl: String,
    val genres: List<Genre>,
    val url: String
)

data class Genre(
    val genreId: String,
    val name: String,
    val url: String
)

fun NetworkAlbumResponse.asDatabaseModel(): Array<DatabaseAlbum>{
    return feed.results.map{
        DatabaseAlbum(
            id = it.id,
            name = it.name,
            artistName = it.artistName,
            genre = it.genres.toString(),
            imageUrl = it.imageUrl,
            releaseDate = it.releaseDate,
            copyright = feed.copyright,
            albumUrl = it.url
        )
    }.toTypedArray()
}

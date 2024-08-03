package com.example.musicapp.repository

import com.example.musicapp.database.DatabaseAlbum
import com.example.musicapp.database.MusicDatabase
import com.example.musicapp.domain.Album
import com.example.musicapp.domain.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MusicRepository(private val database: MusicDatabase) {
    // create albums state flow
    // create refresh suspend function


    // used in the asDomainModel extension function so create the List<Genre> attribute
    suspend fun getGenresByAlbumId(albumId: Long): List<Genre> {
        return withContext(Dispatchers.IO) {
            val genreIds = database.albumGenreJoinDao().getGenreIdsByAlbumId(albumId)
            val genreEntities = database.genreDao().getGenresByIds(genreIds)
            genreEntities.map { Genre(id = it.id, name = it.name, url = it.url) }
        }
    }

    fun List<DatabaseAlbum>.asDomainModel(): List<Album> {
        return map { databaseAlbum ->
            val genres = runBlocking { getGenresByAlbumId(databaseAlbum.id) }
            Album(
                id = databaseAlbum.id,
                name = databaseAlbum.name,
                imageUrl = databaseAlbum.imageUrl,
                artistName = databaseAlbum.artistName,
                releaseDate = databaseAlbum.releaseDate,
                genres = genres
            )
        }
    }

}


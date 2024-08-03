package com.example.musicapp.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getAlbumById(id: Long): DatabaseAlbum?

    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<DatabaseAlbum>
}

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres WHERE id IN (:genreIds)")
    fun getGenresByIds(genreIds: List<Long>): List<DatabaseGenre>

    // MightRemoveLater
    @Query("SELECT * FROM genres")
    suspend fun getAllGenres(): List<DatabaseGenre>
}

@Dao
interface AlbumGenreJoinDao {
    @Query("SELECT genreId FROM album_genre_join WHERE albumId = :albumId")
    fun getGenreIdsByAlbumId(albumId: Long): List<Long>


}

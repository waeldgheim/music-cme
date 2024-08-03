package com.example.musicapp.database

import androidx.room.Dao
import androidx.room.Insert
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
    @Query("SELECT * FROM genres WHERE id = :id")
    suspend fun getGenreById(id: Long): DatabaseGenre?

    // MightRemoveLater
    @Query("SELECT * FROM genres")
    suspend fun getAllGenres(): List<DatabaseGenre>
}

@Dao
interface AlbumGenreJoinDao {
    @Query("SELECT * FROM album_genre_join WHERE albumId = :albumId")
    suspend fun getGenresForAlbum(albumId: Long): List<AlbumGenreJoin>


}

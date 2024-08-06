package com.example.musicapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getAlbumById(id: Long): DatabaseAlbum?

    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<DatabaseAlbum>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg albums: DatabaseAlbum)
}

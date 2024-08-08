package com.example.musicapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums WHERE id = :id")
    fun getAlbumById(id: String): DatabaseAlbum?

    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Flow<List<DatabaseAlbum>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg albums: DatabaseAlbum)

    @Query("DELETE FROM albums")
    suspend fun deleteAllAlbums()
}

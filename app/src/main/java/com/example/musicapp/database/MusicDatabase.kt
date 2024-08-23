package com.example.musicapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseAlbum::class], version = 1)
abstract class MusicDatabase : RoomDatabase() {
    abstract val albumDao: AlbumDao
}

package com.example.musicapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseAlbum::class], version = 1)
abstract class MusicDatabase : RoomDatabase() {
    abstract val albumDao: AlbumDao
}

private lateinit var INSTANCE: MusicDatabase

fun getDatabase(context:Context):MusicDatabase{
    synchronized(MusicDatabase::class.java){
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MusicDatabase::class.java,
                "albumsDatbase").build()
        }
    }
    return INSTANCE
}

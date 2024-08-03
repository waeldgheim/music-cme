package com.example.musicapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseAlbum::class, DatabaseGenre::class, AlbumGenreJoin::class], version = 1)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun albumGenreJoinDao(): AlbumGenreJoinDao
    abstract fun genreDao(): GenreDao

    companion object {
        @Volatile
        private var INSTANCE: MusicDatabase? = null

        fun getDatabase(context: Context): MusicDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicDatabase::class.java,
                    "music_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

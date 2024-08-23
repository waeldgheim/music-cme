package com.example.musicapp.di

import android.content.Context
import androidx.room.Room
import com.example.musicapp.database.MusicDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private lateinit var INSTANCE: MusicDatabase

    @Provides
    @Singleton
    fun provideMusicDatabase(@ApplicationContext context: Context): MusicDatabase {
        synchronized(MusicDatabase::class.java){
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(context,
                    MusicDatabase::class.java,
                    "albumsDatabase").build()
            }
        }
        return INSTANCE
    }
}

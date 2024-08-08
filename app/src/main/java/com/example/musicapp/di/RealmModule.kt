package com.example.musicapp.di

import android.app.Application
import com.example.musicapp.realm.Album
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun provideRealm(application: Application): Realm {
        return Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(Album::class)
            )
        )
    }
}

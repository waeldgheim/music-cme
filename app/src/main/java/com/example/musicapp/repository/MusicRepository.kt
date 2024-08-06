package com.example.musicapp.repository

import com.example.musicapp.database.DatabaseAlbum
import com.example.musicapp.database.MusicDatabase
import com.example.musicapp.network.MusicAppService
import com.example.musicapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val database: MusicDatabase,
    private val musicAppService: MusicAppService) {

    val albums: StateFlow<List<DatabaseAlbum>> = database.albumDao.getAllAlbums()

    suspend fun refreshAlbums(){
        withContext(Dispatchers.IO){
            val allAlbums = musicAppService.getTopHundred()
            database.albumDao.insertAll(*allAlbums.asDatabaseModel())
        }
    }

}


package com.example.musicapp.repository

import android.util.Log
import com.example.musicapp.database.DatabaseAlbum
import com.example.musicapp.database.MusicDatabase
import com.example.musicapp.network.MusicAppService
import com.example.musicapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val database: MusicDatabase,
    private val musicAppService: MusicAppService) {

    var albums: Flow<List<DatabaseAlbum>> = database.albumDao.getAllAlbums()
    lateinit var albumDetails: DatabaseAlbum

    suspend fun refreshAlbums(){
        withContext(Dispatchers.IO){
            try {
                val allAlbums = musicAppService.getTopHundred()
                database.albumDao.insertAll(*allAlbums.asDatabaseModel())
            } catch (e: Exception){
                Log.e("error",e.toString())
            }

        }
    }

    suspend fun getAlbumById (id: String){
        withContext(Dispatchers.IO){
            albumDetails = database.albumDao.getAlbumById(id)!!
        }
    }
}

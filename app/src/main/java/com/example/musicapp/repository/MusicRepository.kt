package com.example.musicapp.repository

import com.example.musicapp.database.DatabaseAlbum
import com.example.musicapp.database.MusicDatabase
import com.example.musicapp.network.MusicAppService
import com.example.musicapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class ApiStatus {LOADING, ERROR, DONE}

class MusicRepository @Inject constructor(
    private val database: MusicDatabase,
    private val musicAppService: MusicAppService) {

    private val _status = MutableStateFlow<ApiStatus>(ApiStatus.LOADING)
    val status: StateFlow<ApiStatus>
        get() = _status

    var albums: Flow<List<DatabaseAlbum>> = database.albumDao.getAllAlbums()
    lateinit var albumDetails: DatabaseAlbum

    suspend fun refreshAlbums(){
        withContext(Dispatchers.IO){
            try {
                _status.value = ApiStatus.LOADING
                val allAlbums = musicAppService.getTopHundred()
                _status.value = ApiStatus.DONE
                database.albumDao.deleteAllAlbums()
                database.albumDao.insertAll(*allAlbums.asDatabaseModel())
            } catch (e: Exception){
                if (albums.first().isEmpty()){
                    _status.value = ApiStatus.ERROR
                }
            }
        }
    }

    suspend fun getAlbumById (id: String){
        withContext(Dispatchers.IO){
            albumDetails = database.albumDao.getAlbumById(id)!!
        }
    }
}

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

    private val databaseMap: MutableMap<String, DatabaseAlbum> = HashMap()
    private val networkMap: MutableMap<String, DatabaseAlbum> = HashMap()

    suspend fun refreshAlbums(){
        withContext(Dispatchers.IO){
            try {
                _status.value = ApiStatus.DONE
                val allAlbums = musicAppService.getTopHundred()

                for (album in albums.first()) {
                    databaseMap[album.id] = album
                }

                for (album in allAlbums.asDatabaseModel()) {
                    networkMap[album.id] = album
                }

                if (networkMap != databaseMap) {
                    _status.value = ApiStatus.LOADING
                    database.albumDao.deleteAllAlbums()
                    database.albumDao.insertAll(*allAlbums.asDatabaseModel())
                    _status.value = ApiStatus.DONE
                }

            } catch (e: Exception){
                if (albums.first().isEmpty()){
                    _status.value = ApiStatus.ERROR
                }
                else{
                    _status.value = ApiStatus.DONE
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

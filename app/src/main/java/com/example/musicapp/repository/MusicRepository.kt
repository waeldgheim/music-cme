package com.example.musicapp.repository

import android.util.Log
import com.example.musicapp.BaseApplication
import com.example.musicapp.network.MusicAppService
import com.example.musicapp.network.asRealmModel
import com.example.musicapp.realm.Album
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val musicAppService: MusicAppService,
    private val realm: Realm
) {
    private val repositoryScope = CoroutineScope(Dispatchers.IO)
    lateinit var albumDetails: Album

    val albums: StateFlow<List<Album>> = realm
        .query<Album>()
        .asFlow()
        .map { results ->
            results.list.toList()
        }
        .stateIn(repositoryScope, SharingStarted.WhileSubscribed(), emptyList())


    fun getAlbumById(id: String) {
        albumDetails = realm
            .query<Album>("id == $0", id)
            .find()
            .first()
    }

    suspend fun refreshAlbums() {
        withContext(Dispatchers.IO) {
            try {
                val networkAlbums = musicAppService.getTopHundred()
                realm.write {
                    deleteAll()
                    networkAlbums.asRealmModel().forEach { album ->
                        copyToRealm(album, updatePolicy = UpdatePolicy.ALL)
                    }
                }

            } catch (e: Exception) {
                Log.e("error in fetching from network: ", e.toString())
            }
        }
    }

    fun clear() { // for later: hande closing realm in hilt
        realm.close()
        repositoryScope.cancel()
    }
}

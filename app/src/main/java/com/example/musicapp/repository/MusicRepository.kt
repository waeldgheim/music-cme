package com.example.musicapp.repository

import com.example.musicapp.network.MusicAppService
import com.example.musicapp.network.asRealmModel
import com.example.musicapp.realm.Album
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class ApiStatus { LOADING, REFRESHING, ERROR, DONE }

class MusicRepository @Inject constructor(
    private val musicAppService: MusicAppService,
    private val realm: Realm
) {
    private val _status = MutableStateFlow<ApiStatus>(ApiStatus.REFRESHING)
    val status: StateFlow<ApiStatus> = _status.asStateFlow()

    private val repositoryScope = CoroutineScope(Dispatchers.IO)
    val albums: StateFlow<List<Album>> = realm
        .query<Album>()
        .asFlow()
        .map { results ->
            results.list.toList()
        }
        .stateIn(repositoryScope, SharingStarted.WhileSubscribed(), emptyList())
    lateinit var albumDetails: Album


    fun getAlbumById(id: String) {
        albumDetails = realm
            .query<Album>("id == $0", id)
            .find()
            .first()
    }

    suspend fun refreshAlbums() {
        withContext(Dispatchers.IO) {
            try {
                _status.value = if (albums.value.isEmpty()) ApiStatus.LOADING else ApiStatus.REFRESHING

                val networkAlbums = musicAppService.getTopHundred().asRealmModel()
                _status.value = ApiStatus.DONE

                if (!areAlbumsEqual(albums.value, networkAlbums)) {
                    realm.write {
                        deleteAll()
                        networkAlbums.forEach { album ->
                            copyToRealm(album, updatePolicy = UpdatePolicy.ALL)
                        }
                    }
                }
            } catch (e: Exception) {
                _status.value = if (albums.value.isEmpty()) ApiStatus.ERROR else ApiStatus.DONE
            }
        }
    }

    private fun List<Album>.toHashMap(): Map<String, Album> {
        return associateBy { it.id }
    }

    private fun areAlbumsEqual(oldList: List<Album>, newList: List<Album>): Boolean {
        val oldMap = oldList.toHashMap()
        val newMap = newList.toHashMap()

        if (oldMap.size != newMap.size) return false

        return oldMap.all { (id, oldAlbum) ->
            val newAlbum = newMap[id]
            newAlbum != null && oldAlbum == newAlbum
        }
    }
}

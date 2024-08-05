package com.example.musicapp.network

import retrofit2.http.GET

interface MusicAppService {
    @GET("api/v2/us/music/most-played/100/albums.json")
    suspend fun getTopHundred(): NetworkAlbumResponse
}

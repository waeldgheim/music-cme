package com.example.musicapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class DatabaseAlbum(
    @PrimaryKey val id: String,
    val name: String,
    val artistName: String,
    val genre: String,
    val imageUrl: String,
    val releaseDate: String,
    val copyright: String,
    val albumUrl: String
)

package com.example.musicapp.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "albums")
data class DatabaseAlbum(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val imageUrl: String,
    val artistName: String,
    val releaseDate: Date,
)

@Entity(tableName = "genres")
data class DatabaseGenre(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val url: String
)

@Entity(
    tableName = "album_genre_join",
    primaryKeys = ["albumId", "genreId"],
    foreignKeys = [
        ForeignKey(entity = DatabaseAlbum::class, parentColumns = ["id"], childColumns = ["albumId"]),
        ForeignKey(entity = DatabaseGenre::class, parentColumns = ["id"], childColumns = ["genreId"])
    ]
)
data class AlbumGenreJoin(
    val albumId: Long,
    val genreId: Long
)

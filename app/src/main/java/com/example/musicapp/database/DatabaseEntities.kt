package com.example.musicapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "albums")
data class DatabaseAlbum(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "artist_name") val artistName: String,
    @ColumnInfo(name = "release_date") val releaseDate: Date,
)

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String
)

@Entity(
    tableName = "album_genre_join",
    primaryKeys = ["albumId", "genreId"],
    foreignKeys = [
        ForeignKey(entity = DatabaseAlbum::class, parentColumns = ["id"], childColumns = ["albumId"]),
        ForeignKey(entity = Genre::class, parentColumns = ["id"], childColumns = ["genreId"])
    ]
)
data class AlbumGenreJoin(
    @ColumnInfo(name = "albumId") val albumId: Long,
    @ColumnInfo(name = "genreId") val genreId: Long
)

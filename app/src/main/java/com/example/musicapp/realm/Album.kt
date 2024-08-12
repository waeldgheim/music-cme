package com.example.musicapp.realm

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Album: RealmObject {
    @PrimaryKey var id: String = ""
    var name: String = ""
    var artistName: String = ""
    var imageUrl: String? = ""
    var releaseDate: String = ""
    var genres: RealmList<String> = realmListOf()
    var copyright: String = ""
    var url: String? = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Album

        return id == other.id &&
                name == other.name &&
                artistName == other.artistName &&
                imageUrl == other.imageUrl &&
                releaseDate == other.releaseDate &&
                areGenresEqual(genres, other.genres) &&
                copyright == other.copyright &&
                url == other.url
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    private fun areGenresEqual(list1: RealmList<String>, list2: RealmList<String>): Boolean {
        if (list1.size != list2.size) return false
        return list1.zip(list2).all { (a, b) -> a == b }
    }
}

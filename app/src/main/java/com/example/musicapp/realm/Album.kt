package com.example.musicapp.realm

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Album: RealmObject {
    @PrimaryKey var id: String = ""
    var name: String = ""
    var artistName: String = ""
    var imageUrl: String = ""
    var releaseData: String = ""
    var genres: RealmList<String> = realmListOf()
}
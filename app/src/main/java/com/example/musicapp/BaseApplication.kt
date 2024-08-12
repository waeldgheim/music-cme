package com.example.musicapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.kotlin.Realm
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication: Application() {
    @Inject
    lateinit var realm: Realm

    override fun onTerminate() {
        super.onTerminate()
        realm.close()
    }
}

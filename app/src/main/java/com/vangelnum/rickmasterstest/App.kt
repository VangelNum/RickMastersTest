package com.vangelnum.rickmasterstest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("main.db")
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}
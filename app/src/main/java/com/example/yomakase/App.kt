package com.example.yomakase

import android.app.Application
import com.example.yomakase.model.SharedPrefs

class App: Application() {
    companion object{
        lateinit var prefs: SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }
}
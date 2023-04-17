package com.example.yomakase.model

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPrefs(context: Context) {
    private val prefsName = "accountInfo"
    private val prefs = context.getSharedPreferences(prefsName, MODE_PRIVATE)

    var autoLogin: Boolean?
        get() = prefs.getBoolean("autoLogin", false)
        set(value) {
            prefs.edit().putBoolean("autoLogin", value!!).apply()
        }

    var accessToken: String?
        get() = prefs.getString("accessToken", null)
        set(value) {
            prefs.edit().putString("accessToken", value!!).apply()
        }
}
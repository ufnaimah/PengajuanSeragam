package com.example.seragamstismobile.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("SeragamPrefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun fetchAuthToken(): String? = prefs.getString("jwt_token", null)

    fun getToken(): String? = fetchAuthToken()

    // Kita pakai nama clearSession() biar konsisten
    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
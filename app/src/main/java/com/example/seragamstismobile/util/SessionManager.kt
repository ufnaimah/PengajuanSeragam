package com.example.seragamstismobile.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("SeragamPrefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    // Gunakan nama ini agar konsisten dengan kode Activity
    fun fetchAuthToken(): String? = prefs.getString("jwt_token", null)

    // Tetap simpan getToken agar tidak merusak kode lain jika ada
    fun getToken(): String? = fetchAuthToken()

    fun clear() {
        prefs.edit().clear().apply()
    }
}
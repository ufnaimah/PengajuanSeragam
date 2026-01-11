package com.example.seragamstismobile.api

import android.content.Context
import com.example.seragamstismobile.util.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.59.152.41:8080/"

    // Kita buat instance malas (lazy) agar bisa diakses tanpa context setiap saat
    // Untuk interceptor, kita bisa buatkan cara lain atau masukkan context di awal
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }

    // Tetap sediakan fungsi lama jika kamu ingin menggunakannya
    fun getApiService(context: Context): ApiService = instance
}
package com.example.seragamstismobile.api

import android.content.Context
import com.example.seragamstismobile.util.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    // Pastikan IP ini sesuai dengan yang berhasil kamu pakai untuk Login
    private const val BASE_URL = "http://10.59.152.41:8080/"

    // Fungsi ini kita ubah agar bisa mengambil token dari Context
    fun getApiService(context: Context): ApiService {

        // 1. Ambil Token yang tersimpan saat Login berhasil
        val sessionManager = SessionManager(context)
        val token = sessionManager.getToken()

        // 2. Buat 'Tukang Pos' (Client) yang pintar
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()

                // LOGIKA PENTING:
                // Jika token ada (User sudah login) -> Tempel Token di Header
                // Jika token kosong (Saat Register/Login) -> Jangan tempel apa-apa (Aman!)
                if (!token.isNullOrEmpty()) {
                    requestBuilder.header("Authorization", "Bearer $token")
                }

                chain.proceed(requestBuilder.build())
            }
            // Tambahkan timeout biar tidak cepat error kalau sinyal lambat
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        // 3. Kembalikan Retrofit dengan Client yang sudah membawa token
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}
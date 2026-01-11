package com.example.seragamstismobile.api

import android.content.Context
import com.example.seragamstismobile.util.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    fun getApiService(context: Context): ApiService {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val token = SessionManager(context).getToken()
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
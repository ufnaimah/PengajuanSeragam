package com.example.seragamstismobile.api

import com.example.seragamstismobile.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/pengajuan/saya")
    suspend fun getMyPengajuan(): Response<List<Pengajuan>>

    @POST("api/pengajuan")
    suspend fun createPengajuan(@Body req: Any): Response<Any>
}
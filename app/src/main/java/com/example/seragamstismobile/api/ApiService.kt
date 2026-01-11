package com.example.seragamstismobile.api

import com.example.seragamstismobile.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Sesuai dengan AuthController: /api/auth/signin
    @POST("api/auth/signin")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // Sesuai dengan AuthController: /api/auth/signup
    @POST("api/auth/signup")
    suspend fun register(@Body request: RegisterRequest): Response<MessageResponse>

    // Sesuai dengan UserController: /api/users/profile
    @GET("api/users/profile")
    suspend fun getProfile(): Response<UserResponse>

    // Sesuai dengan UserController: /api/users/profile (Method PUT)
    @PUT("api/users/profile")
    suspend fun updateProfile(@Body request: UserResponse): Response<UserResponse>

    // Sesuai dengan PengajuanController: /api/pengajuan/saya
    @GET("api/pengajuan/saya")
    suspend fun getMyPengajuan(): Response<List<Pengajuan>>
}
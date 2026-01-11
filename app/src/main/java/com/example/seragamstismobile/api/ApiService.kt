package com.example.seragamstismobile.api

import com.example.seragamstismobile.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // 1. Authentication
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<Any>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // 2. User Management (Butuh Token)
    @GET("api/users/profile")
    suspend fun getProfile(): Response<UserResponse>

    @PUT("api/users/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<Any>

    @PUT("api/users/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<Any>

    @DELETE("api/users/account")
    suspend fun deleteAccount(): Response<Any>

    // 3. Business Process (Pengajuan - Mahasiswa)
    @POST("api/pengajuan")
    suspend fun createPengajuan(@Body request: PengajuanRequest): Response<Any>

    @GET("api/pengajuan/saya")
    suspend fun getMyPengajuan(): Response<List<PengajuanResponse>>

    // 4. Business Process (Pengajuan - Admin)
    @GET("api/pengajuan/admin/all")
    suspend fun getAllPengajuan(): Response<List<PengajuanResponse>>

    @PUT("api/pengajuan/admin/{id}/status")
    suspend fun updateStatus(
        @Path("id") id: Long,
        @Body request: UpdateStatusRequest
    ): Response<Any>
}
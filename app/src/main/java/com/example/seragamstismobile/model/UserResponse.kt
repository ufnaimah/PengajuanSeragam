package com.example.seragamstismobile.model

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val namaLengkap: String, // Diubah dari 'name' agar tidak error
    val nim: String,         // Diubah dari 'identityNumber' agar tidak error
    val phoneNumber: String,
    val gender: String
)
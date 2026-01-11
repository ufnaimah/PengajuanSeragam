package com.example.seragamstismobile.model

data class RegisterRequest(
    val username: String,
    val password: String,
    val namaLengakap: String, // Mengikuti JSON backend
    val nim: String,
    val gender: String, // "LAKI_LAKI" atau "PEREMPUAN"
    val isHijab: Boolean // true atau false
)
package com.example.seragamstismobile.model

data class RegisterRequest(
    val username: String,
    val password: String,
    val namaLengkap: String, // Perbaikan typo dari 'namaLengakap'
    val nim: String,
    val gender: String,
    val isHijab: Boolean // Ganti 'hijab' jadi 'isHijab' agar sesuai Activity
)
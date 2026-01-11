package com.example.seragamstismobile.model

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val name: String,
    val identityNumber: String,
    val phoneNumber: String,
    val gender: String
)
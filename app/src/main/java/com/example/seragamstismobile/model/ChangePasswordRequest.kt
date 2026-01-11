package com.example.seragamstismobile.model

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
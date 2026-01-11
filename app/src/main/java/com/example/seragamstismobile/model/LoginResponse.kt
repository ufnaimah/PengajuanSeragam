package com.example.seragamstismobile.model
data class LoginResponse
    (val token: String,
     val username: String,
     val roles: List<String>)
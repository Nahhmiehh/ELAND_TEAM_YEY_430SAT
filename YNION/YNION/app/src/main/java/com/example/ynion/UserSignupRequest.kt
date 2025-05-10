package com.example.ynion

data class UserSignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val profilePicture: String = "Default"
)

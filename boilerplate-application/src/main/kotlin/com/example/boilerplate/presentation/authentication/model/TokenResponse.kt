package com.example.boilerplate.presentation.authentication.model

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)

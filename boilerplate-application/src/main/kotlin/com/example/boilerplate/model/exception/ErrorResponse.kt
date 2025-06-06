package com.example.boilerplate.model.exception

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class ErrorResponse(
    val code: String,
    val message: String,
)

package com.example.boilerplate.model.account.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateAccountRequest(
    @field:NotBlank
    val name: String,
    @field:Email
    @field:NotBlank
    val email: String,
)

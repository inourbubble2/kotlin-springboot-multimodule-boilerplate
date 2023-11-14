package com.example.boilerplate.model.account.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CreateAccountRequest(
    @field:NotBlank
    val name: String,

    @field:Email
    @field:NotBlank
    val email: String,
)

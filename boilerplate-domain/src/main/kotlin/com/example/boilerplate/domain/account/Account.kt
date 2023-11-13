package com.example.boilerplate.domain.account

class Account(
    val accountId: Long,
    val name: String,
    val email: String,
    val status: AccountStatus,
)

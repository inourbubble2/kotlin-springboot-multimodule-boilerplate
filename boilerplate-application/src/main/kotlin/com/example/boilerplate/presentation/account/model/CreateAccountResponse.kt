package com.example.boilerplate.presentation.account.model

import com.example.boilerplate.domain.account.Account
import com.example.boilerplate.domain.account.AccountStatus

data class CreateAccountResponse(
    val name: String,
    val email: String,
    val status: AccountStatus,
) {
    companion object {
        fun of(account: Account) =
            CreateAccountResponse(
                name = account.name,
                email = account.email,
                status = account.status,
            )
    }
}

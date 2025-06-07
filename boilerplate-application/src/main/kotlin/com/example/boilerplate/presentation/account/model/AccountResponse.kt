package com.example.boilerplate.presentation.account.model

import com.example.boilerplate.domain.account.Account
import com.example.boilerplate.domain.account.AccountStatus

data class AccountResponse(
    val id: Long,
    val name: String,
    val email: String,
    val status: AccountStatus,
) {
    companion object {
        fun of(account: Account) =
            AccountResponse(
                id = account.id,
                name = account.name,
                email = account.email,
                status = account.status,
            )
    }
}

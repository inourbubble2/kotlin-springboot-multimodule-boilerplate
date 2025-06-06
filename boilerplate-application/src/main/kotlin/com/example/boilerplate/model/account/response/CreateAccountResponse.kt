package com.example.boilerplate.model.account.response

import com.example.boilerplate.database.entity.Account
import com.example.boilerplate.database.model.AccountStatus

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

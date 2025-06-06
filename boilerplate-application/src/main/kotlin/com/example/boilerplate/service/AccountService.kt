package com.example.boilerplate.service

import com.example.boilerplate.domain.account.Account
import com.example.boilerplate.domain.account.AccountRepository
import com.example.boilerplate.domain.account.AccountStatus
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {
    fun createAccount(
        name: String,
        email: String,
    ): Account {
        val entity =
            Account(
                name = name,
                email = email,
                status = AccountStatus.ACTIVE,
            )

        return accountRepository.save(entity)
    }
}

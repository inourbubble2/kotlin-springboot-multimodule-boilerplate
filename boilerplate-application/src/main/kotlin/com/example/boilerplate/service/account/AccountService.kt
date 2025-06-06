package com.example.boilerplate.service.account

import com.example.boilerplate.database.entity.Account
import com.example.boilerplate.database.model.AccountStatus
import com.example.boilerplate.database.repository.AccountRepository
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

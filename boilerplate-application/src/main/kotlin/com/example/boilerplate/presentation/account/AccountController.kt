package com.example.boilerplate.presentation.account

import com.example.boilerplate.domain.account.Account
import com.example.boilerplate.presentation.account.model.AccountResponse
import com.example.boilerplate.presentation.account.model.CreateAccountRequest
import com.example.boilerplate.presentation.authentication.AuthenticatedAccount
import com.example.boilerplate.service.AccountService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController(
    private val service: AccountService,
) {
    @GetMapping("/authenticated")
    fun getAuthenticatedAccount(
        @AuthenticatedAccount account: Account,
    ) = AccountResponse.Companion.of(account)

    @PostMapping
    fun createAccount(
        @RequestBody @Valid request: CreateAccountRequest,
    ): AccountResponse {
        val account = service.createAccount(request.name, request.email)
        return AccountResponse.Companion.of(account)
    }
}

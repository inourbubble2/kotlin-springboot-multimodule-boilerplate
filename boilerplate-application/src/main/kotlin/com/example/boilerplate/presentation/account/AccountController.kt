package com.example.boilerplate.presentation.account

import com.example.boilerplate.presentation.account.model.CreateAccountRequest
import com.example.boilerplate.presentation.account.model.CreateAccountResponse
import com.example.boilerplate.service.AccountService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val service: AccountService,
) {
    @PostMapping("/account")
    fun createAccount(
        @RequestBody @Valid request: CreateAccountRequest,
    ): CreateAccountResponse {
        val account = service.createAccount(request.name, request.email)
        return CreateAccountResponse.Companion.of(account)
    }
}

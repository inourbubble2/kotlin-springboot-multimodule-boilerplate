package com.example.boilerplate.web.account

import com.example.boilerplate.model.account.request.CreateAccountRequest
import com.example.boilerplate.model.account.response.CreateAccountResponse
import com.example.boilerplate.service.account.AccountService
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
        return CreateAccountResponse.of(account)
    }
}

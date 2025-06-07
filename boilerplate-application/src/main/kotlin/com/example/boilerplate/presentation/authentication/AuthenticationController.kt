package com.example.boilerplate.presentation.authentication

import com.example.boilerplate.domain.account.Account
import com.example.boilerplate.presentation.authentication.model.LoginRequest
import com.example.boilerplate.presentation.authentication.model.TokenReissueRequest
import com.example.boilerplate.presentation.authentication.model.TokenResponse
import com.example.boilerplate.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
) {
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
    ): TokenResponse = authenticationService.login(request.name, request.email)

    @PostMapping("/reissue")
    fun reissue(
        @AuthenticatedAccount account: Account,
        @RequestBody request: TokenReissueRequest,
    ) {
        authenticationService.reissue(LocalDateTime.now(), request.refreshToken, account.id)
    }
}

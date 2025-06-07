package com.example.boilerplate.service

import com.example.boilerplate.common.exception.BoilerplateException
import com.example.boilerplate.common.exception.ErrorCode
import com.example.boilerplate.domain.account.AccountRepository
import com.example.boilerplate.presentation.authentication.model.TokenResponse
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthenticationService(
    private val jwtProvider: JwtProvider,
    private val accountRepository: AccountRepository,
) {
    fun login(
        name: String,
        email: String,
    ): TokenResponse {
        val account =
            accountRepository.findByNameAndEmail(email, name)
                ?: throw BoilerplateException(ErrorCode.UNAUTHORIZED)
        val accessToken = jwtProvider.generateAccessToken(LocalDateTime.now(), account.id)
        val refreshToken = jwtProvider.generateRefreshToken(LocalDateTime.now(), account.id)
        return TokenResponse(accessToken, refreshToken)
    }

    fun reissue(
        issuedAt: LocalDateTime,
        refreshToken: String,
        accountId: Long,
    ): TokenResponse {
        val accessToken = jwtProvider.reissue(issuedAt, refreshToken)
        val refreshToken = jwtProvider.generateRefreshToken(issuedAt, accountId)
        return TokenResponse(accessToken, refreshToken)
    }
}

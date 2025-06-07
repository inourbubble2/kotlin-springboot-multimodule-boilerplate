package com.example.boilerplate.service

import com.example.boilerplate.common.exception.BoilerplateException
import com.example.boilerplate.common.exception.ErrorCode
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Base64
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${jwt.access-token-secret-key}")
    private val accessTokenSecretKey: String,
    @Value("\${jwt.refresh-token-secret-key}")
    private val refreshTokenSecretKey: String,
) {
    private fun getAccessSecretKey(): SecretKey {
        val encoded =
            Base64.getEncoder().encodeToString(accessTokenSecretKey.toByteArray())
        return Keys.hmacShaKeyFor(encoded.toByteArray())
    }

    private fun getRefreshSecretKey(): SecretKey {
        val encoded =
            Base64.getEncoder().encodeToString(refreshTokenSecretKey.toByteArray())
        return Keys.hmacShaKeyFor(encoded.toByteArray())
    }

    fun reissue(
        now: LocalDateTime,
        refreshToken: String,
    ): String {
        val accountId: String =
            Jwts
                .parserBuilder()
                .setSigningKey(getRefreshSecretKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody()
                .subject

        return generateAccessToken(now, accountId.toLong())
    }

    fun generateAccessToken(
        now: LocalDateTime,
        accountId: Long,
    ): String {
        val instant = now.toInstant(ZoneOffset.UTC)

        return Jwts
            .builder()
            .setSubject(accountId.toString())
            .setIssuedAt(Date.from(instant))
            .setExpiration(Date.from(instant.plusMillis(ACCESS_TOKEN_EXPIRES)))
            .signWith(getAccessSecretKey(), SignatureAlgorithm.HS512)
            .compact()
    }

    fun generateRefreshToken(
        now: LocalDateTime,
        accountId: Long,
    ): String {
        val instant = now.toInstant(ZoneOffset.UTC)

        return Jwts
            .builder()
            .setSubject(accountId.toString())
            .setIssuedAt(Date.from(instant))
            .setExpiration(Date.from(instant.plusMillis(REFRESH_TOKEN_EXPIRES)))
            .signWith(getRefreshSecretKey(), SignatureAlgorithm.HS512)
            .compact()
    }

    fun getSubject(accessToken: String?): String =
        Jwts
            .parserBuilder()
            .setSigningKey(getAccessSecretKey())
            .build()
            .parseClaimsJws(accessToken)
            .getBody()
            .subject

    fun getAccessToken(request: HttpServletRequest): String {
        val authorization = request.getHeader("Authorization")
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw BoilerplateException(ErrorCode.UNAUTHORIZED)
        }
        return authorization.substring(7)
    }

    companion object {
        private const val ACCESS_TOKEN_EXPIRES = 3600000L
        private const val REFRESH_TOKEN_EXPIRES = 3600000000L
    }
}

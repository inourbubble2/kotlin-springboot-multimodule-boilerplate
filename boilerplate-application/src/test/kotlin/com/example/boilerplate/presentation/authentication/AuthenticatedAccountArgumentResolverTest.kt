package com.example.boilerplate.presentation.authentication

import com.example.boilerplate.domain.account.Account
import com.example.boilerplate.domain.account.AccountRepository
import com.example.boilerplate.domain.account.AccountStatus
import com.example.boilerplate.service.JwtProvider
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Test
import org.springframework.core.MethodParameter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.context.request.NativeWebRequest

class AuthenticatedAccountArgumentResolverTest {
    private val jwtProvider: JwtProvider = mockk()
    private val accountRepository: AccountRepository = mockk()
    private val resolver = AuthenticatedAccountArgumentResolver(jwtProvider, accountRepository)

    @Test
    fun `AuthenticatedAccount 어노테이션이 있고 Account가 존재하면 Account를 반환한다`() {
        val accessToken = "token"
        val accountId = "123"
        val account = Account(name = "Account", email = "email@email.com", status = AccountStatus.ACTIVE, id = 123L)

        val methodParameter = mockMethodParameter(AuthenticatedAccount::class.java, Account::class.java)
        val nativeWebRequest = mockWebRequest()

        every { jwtProvider.getAccessToken(any()) } returns accessToken
        every { jwtProvider.getSubject(accessToken) } returns accountId
        every { accountRepository.findByIdOrNull(accountId.toLong()) } returns account

        resolver.resolveArgument(methodParameter, null, nativeWebRequest, null) shouldBe account
    }

    @Test
    fun `AuthenticatedAccount 어노테이션이 있고 Account가 없으면 예외가 발생한다`() {
        val accessToken = "token"
        val accountId = "123"

        val methodParameter = mockMethodParameter(AuthenticatedAccount::class.java, Account::class.java)
        val nativeWebRequest = mockWebRequest()

        every { jwtProvider.getAccessToken(any()) } returns accessToken
        every { jwtProvider.getSubject(accessToken) } returns accountId
        every { accountRepository.findByIdOrNull(accountId.toLong()) } returns null

        shouldThrow<Exception> {
            resolver.resolveArgument(methodParameter, null, nativeWebRequest, null)
        }
    }

    @Test
    fun `NullableAuthenticatedAccount 어노테이션이 있고 Account가 없으면 null을 반환한다`() {
        val accessToken = "token"
        val accountId = "123"

        val methodParameter = mockMethodParameter(NullableAuthenticatedAccount::class.java, Account::class.java)
        val nativeWebRequest = mockWebRequest()

        every { jwtProvider.getAccessToken(any()) } returns accessToken
        every { jwtProvider.getSubject(accessToken) } returns accountId
        every { accountRepository.findByIdOrNull(accountId.toLong()) } returns null

        val result = resolver.resolveArgument(methodParameter, null, nativeWebRequest, null)
        result shouldBe null
    }

    @Test
    fun `NullableAuthenticatedAccount 어노테이션이 있고 Account가 존재하면 Account를 반환한다`() {
        val accessToken = "token"
        val accountId = "123"
        val account = Account(name = "Account", email = "email@email.com", status = AccountStatus.ACTIVE, id = 123L)

        val methodParameter = mockMethodParameter(NullableAuthenticatedAccount::class.java, Account::class.java)
        val nativeWebRequest = mockWebRequest()

        every { jwtProvider.getAccessToken(any()) } returns accessToken
        every { jwtProvider.getSubject(accessToken) } returns accountId
        every { accountRepository.findByIdOrNull(accountId.toLong()) } returns account

        resolver.resolveArgument(methodParameter, null, nativeWebRequest, null) shouldBe account
    }

    private fun mockMethodParameter(
        annotationClass: Class<out Annotation>,
        parameterType: Class<*>,
    ): MethodParameter {
        val methodParameter = mockk<MethodParameter>(relaxed = true)
        every { methodParameter.hasParameterAnnotation(annotationClass) } returns true
        every { methodParameter.hasParameterAnnotation(any<Class<Annotation>>()) } answers {
            firstArg<Class<Annotation>>() == annotationClass
        }
        every { methodParameter.parameterType } returns parameterType
        return methodParameter
    }

    private fun mockWebRequest(): NativeWebRequest {
        val httpServletRequest = mockk<HttpServletRequest>(relaxed = true)
        val nativeWebRequest = mockk<NativeWebRequest>(relaxed = true)
        every { nativeWebRequest.nativeRequest } returns httpServletRequest
        return nativeWebRequest
    }
}

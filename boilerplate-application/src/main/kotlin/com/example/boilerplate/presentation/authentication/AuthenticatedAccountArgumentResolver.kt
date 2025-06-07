package com.example.boilerplate.presentation.authentication

import com.example.boilerplate.common.exception.BoilerplateException
import com.example.boilerplate.common.exception.ErrorCode
import com.example.boilerplate.domain.account.Account
import com.example.boilerplate.domain.account.AccountRepository
import com.example.boilerplate.service.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthenticatedAccountArgumentResolver(
    private val jwtProvider: JwtProvider,
    private val accountRepository: AccountRepository,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasAnnotation =
            parameter.hasParameterAnnotation(AuthenticatedAccount::class.java) ||
                parameter.hasParameterAnnotation(NullableAuthenticatedAccount::class.java)
        val isAssignable = Account::class.java.isAssignableFrom(parameter.parameterType)

        return hasAnnotation && isAssignable
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Account? {
        val request = webRequest.nativeRequest as HttpServletRequest
        val account =
            runCatching {
                val accessToken: String = jwtProvider.getAccessToken(request)
                val accountId: String = jwtProvider.getSubject(accessToken)
                accountRepository.findByIdOrNull(accountId.toLong())
            }.getOrNull()

        if (account == null && parameter.hasParameterAnnotation(AuthenticatedAccount::class.java)) {
            throw BoilerplateException(ErrorCode.UNAUTHORIZED)
        }
        return account
    }
}

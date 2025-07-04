package com.example.boilerplate.presentation

import com.example.boilerplate.presentation.authentication.AuthenticatedAccountArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authenticatedAccountArgumentResolver: AuthenticatedAccountArgumentResolver,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver?>) {
        resolvers.add(authenticatedAccountArgumentResolver)
    }
}

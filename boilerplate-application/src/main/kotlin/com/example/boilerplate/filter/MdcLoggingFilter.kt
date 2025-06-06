package com.example.boilerplate.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Component
class MdcLoggingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val requestId = UUID.randomUUID().toString().substring(0, 8)
            MDC.put(REQUEST_ID, requestId)

            logger.info("[$requestId]: ${request.method} ${request.requestURI}")

            filterChain.doFilter(request, response)
        } finally {
            MDC.remove(REQUEST_ID)
        }
    }

    companion object {
        private const val REQUEST_ID = "requestId"
    }
}

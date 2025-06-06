package com.example.boilerplate.handler

import com.example.boilerplate.common.exception.BoilerplateException
import com.example.boilerplate.common.exception.ErrorCode
import com.example.boilerplate.model.exception.ErrorResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun boilerplateExceptionHandler(ex: BoilerplateException): ResponseEntity<ErrorResponse> {
        logger.error(ex) { ex.message }
        return ResponseEntity
            .status(ex.status)
            .body(ErrorResponse(ex.code, ex.message))
    }

    @ExceptionHandler
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logger.error(ex) { ex.message }
        val message =
            ex.bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
        return ResponseEntity
            .status(400)
            .body(ErrorResponse(ErrorCode.BAD_REQUEST.name, message))
    }

    @ExceptionHandler
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error(ex) { ex.message }
        val message = ex.message ?: ErrorCode.INTERNAL_SERVER_ERROR.message
        return ResponseEntity
            .status(500)
            .body(ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.name, message))
    }
}

package com.example.boilerplate.common.exception

class BoilerplateException(
    val code: String,
    override val message: String,
    val status: Int,
) : RuntimeException() {
    constructor(errorCode: ErrorCode) : this(errorCode.name, errorCode.message, errorCode.status)
}

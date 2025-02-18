package com.example.network.model

data class ErrorResponse(
    val httpStatus: String,
    val code: Int,
    val message: String,
    val errors: ErrorContent,
)

data class ErrorContent(
    val field: String,
    val message: String,
)

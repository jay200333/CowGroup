package com.example.network.model

data class ApiResponse<T>(
    val httpStatus: String,
    val code: Int,
    val message: String,
    val data: T,
)

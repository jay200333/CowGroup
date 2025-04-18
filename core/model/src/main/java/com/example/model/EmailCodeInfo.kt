package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class EmailCodeInfo(
    val email: String,
    val code: String
)
package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateMeeting(
    val name: String,
    val category: String,
    val capacity: Int,
    val content: String,
    val file: String
)
package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateEvent(
    val name: String,
    val category: String,
    val location: String,
    val eventDate: String,
    val capacity: Int,
    val content: String
)
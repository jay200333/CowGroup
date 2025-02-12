package com.example.model

data class CreateEvent(
    val name: String,
    val category: String,
    val location: String,
    val eventDate: String,
    val capacity: Int,
    val content: String
)
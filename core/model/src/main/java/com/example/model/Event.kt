package com.example.model

data class Event(
    val id: Int,
    val name: String,
    val content: String,
    val category: String,
    val applicants: Int,
    val capacity: Int,
    val imageUri: String,
    val isBookmarked: Boolean,
)

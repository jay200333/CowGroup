package com.example.model

data class Comment(
    val id: Int,
    val username: String,
    val daysAgo: String,
    val content: String,
    val isRegistrant: Boolean
)

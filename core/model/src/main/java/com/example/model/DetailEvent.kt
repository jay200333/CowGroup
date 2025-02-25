package com.example.model

data class DetailEvent(
    val id: Int,
    val name: String,
    val author: String,
    val category: String,
    val createdDate: String,
    val location: String,
    val content: String,
    val eventDate: String,
    val capacity: Int,
    val applicants: Int,
    val isBookmarked: Boolean
)

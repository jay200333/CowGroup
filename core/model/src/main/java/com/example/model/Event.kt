package com.example.model

data class Event(
    val id: Int,
    val name: String,
    val content: String,
    val eventDate: String,
    val createDate: String,
    val participants: Int,
    val capacities: Int,
    val isBookmarked: Boolean
)

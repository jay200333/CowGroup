package com.example.model

data class Event(
    val eventName: String,
    val content: String,
    val eventDate: String,
    val createDate: String,
    val applicants: Int,
    val participants: Int,
)

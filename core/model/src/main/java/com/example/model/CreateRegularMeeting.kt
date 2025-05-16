package com.example.model

data class CreateRegularMeeting(
    val name: String,
    val dateTime: String,
    val location: String,
    val capacity: Int
)

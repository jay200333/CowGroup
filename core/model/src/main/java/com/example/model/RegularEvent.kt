package com.example.model

data class RegularEvent(
    val id: Int,
    val participationId: Int,
    val name: String,
    val location: String,
    val dateTime: String,
    val capacity: Int,
    val applicants: Int,
    val isRegularRegistrant: Boolean
)

package com.example.model

data class SearchRegularEvent(
    val id: Int,
    val name: String,
    val category: String,
    val accessUrl: String,
    val dateTime: String,
    val capacity: Int,
    val applicants: Int
)

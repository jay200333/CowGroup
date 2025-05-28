package com.example.model

data class Post(
    val id: Int,
    val title: String,
    val content: String,
    val dateTime: String,
    val userName: String,
    val commentCount: Int,
    val isRegistrant: Boolean
)
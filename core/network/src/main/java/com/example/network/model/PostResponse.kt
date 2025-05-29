package com.example.network.model

import com.example.model.CreatePost

data class PostResponse(
    val subject: String,
    val content: String
)

fun PostResponse.toCreatePost(): CreatePost = CreatePost(
    subject = subject,
    content = content
)

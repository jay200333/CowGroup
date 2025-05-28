package com.example.data.repository

import com.example.model.CreatePost

interface PostRepository {
    suspend fun createPost(eventId: Int, createPost: CreatePost)
}
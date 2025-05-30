package com.example.data.repository

interface CommentRepository {
    suspend fun createComment(postId: Int, content: String)
}
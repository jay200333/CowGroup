package com.example.data.repository

import com.example.model.Comment

interface CommentRepository {
    suspend fun createComment(postId: Int, content: String)

    suspend fun getCommentList(postId: Int): List<Comment>

    suspend fun deleteComment(commentId: Int)

    suspend fun editComment(commentId: Int, content: String)
}
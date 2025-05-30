package com.example.data.repository

import com.example.model.Comment
import com.example.network.model.toComment
import com.example.network.retrofit.CowGroupApi
import retrofit2.HttpException
import javax.inject.Inject

internal class CommentRepositoryImpl @Inject constructor(
    private val api: CowGroupApi
) : CommentRepository {
    override suspend fun createComment(postId: Int, content: String) {
        try {
            api.createComment(postId, content)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCommentList(postId: Int): List<Comment> {
        try {
            val response = api.getCommentList(postId)
            return response.data.comments.map { it.toComment() }
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}
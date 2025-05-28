package com.example.data.repository

import com.example.model.CreatePost
import com.example.network.retrofit.CowGroupApi
import retrofit2.HttpException
import javax.inject.Inject

internal class PostRepositoryImpl @Inject constructor(
    private val api: CowGroupApi
) : PostRepository {
    override suspend fun createPost(eventId: Int, createPost: CreatePost) {
        try {
            api.createPost(eventId, createPost)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}
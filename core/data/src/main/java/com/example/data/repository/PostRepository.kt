package com.example.data.repository

import androidx.paging.PagingData
import com.example.model.CreatePost
import com.example.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun createPost(eventId: Int, createPost: CreatePost)

    fun getPagingPosts(pageSize: Int, eventId: Int): Flow<PagingData<Post>>
}
package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.paging.PostPagingSource
import com.example.model.CreatePost
import com.example.model.Post
import com.example.network.retrofit.CowGroupApi
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

internal class PostRepositoryImpl @Inject constructor(
    private val api: CowGroupApi
) : PostRepository {
    override fun getPagingPosts(pageSize: Int, eventId: Int): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostPagingSource(api, eventId) }
        ).flow
    }

    override suspend fun createPost(eventId: Int, createPost: CreatePost) {
        try {
            api.createPost(eventId, createPost)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    companion object {
        private const val INITIAL_LOAD_SIZE = 10
    }
}
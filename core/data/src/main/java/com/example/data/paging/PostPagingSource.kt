package com.example.data.paging

import com.example.model.Post
import com.example.network.model.toPost
import com.example.network.retrofit.CowGroupApi

class PostPagingSource(
    private val api: CowGroupApi,
    private val eventId: Int
) : BasePagingSource<Post>(
    pageFetcher = { page, size ->
        api.getPagingPosts(
            eventId = eventId,
            page = page,
            size = size,
        ).data.postInfos.map { it.toPost() }
    }
)
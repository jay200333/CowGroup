package com.example.data.paging

import com.example.network.model.toEvent
import com.example.network.retrofit.CowGroupApi

class HomeEventPagingSource(
    private val api: CowGroupApi
) : BaseEventPagingSource(pageFetcher = { page, size ->
    api.getEvents(
        page,
        size
    ).data.content.map { it.toEvent() }
})

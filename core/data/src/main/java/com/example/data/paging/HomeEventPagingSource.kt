package com.example.data.paging

import com.example.model.Event
import com.example.network.model.toEvent
import com.example.network.retrofit.CowGroupApi

class HomeEventPagingSource(
    private val api: CowGroupApi
) : BasePagingSource<Event>(pageFetcher = { page, size ->
    api.getEvents(
        page,
        size
    ).data.content.map { it.toEvent() }
})

package com.example.data.paging

import com.example.model.Event
import com.example.network.model.toEvent
import com.example.network.retrofit.CowGroupApi

class BookmarkEventPagingSource(private val api: CowGroupApi) :
    BasePagingSource<Event>(pageFetcher = { page, size ->
        api.getBookmarkEvents(page, size).data.bookmarkEvents.map { it.toEvent() }
    })
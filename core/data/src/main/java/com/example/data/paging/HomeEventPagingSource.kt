package com.example.data.paging

import com.example.model.Event
import com.example.model.SearchMeetingRequest
import com.example.network.model.toEvent
import com.example.network.retrofit.CowGroupApi

class HomeEventPagingSource(
    private val api: CowGroupApi,
    private val searchRequest: SearchMeetingRequest
) : BasePagingSource<Event>(pageFetcher = { page, size ->
    api.getSearchEvents(
        page,
        size,
        searchRequest
    ).data.eventSearchInfos.map { it.toEvent() }
})

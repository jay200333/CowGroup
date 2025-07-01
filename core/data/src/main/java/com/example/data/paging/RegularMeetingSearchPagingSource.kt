package com.example.data.paging

import com.example.model.SearchRegularEvent
import com.example.model.SearchRegularMeetingRequest
import com.example.network.model.toSearchRegularEvent
import com.example.network.retrofit.CowGroupApi

class RegularMeetingSearchPagingSource(
    private val api: CowGroupApi,
    private val searchRequest: SearchRegularMeetingRequest
) : BasePagingSource<SearchRegularEvent>(pageFetcher = { page, size ->
    api.getSearchRegularMeeting(
        page,
        size,
        searchRequest
    ).data.searchInfos.map { it.toSearchRegularEvent() }
})
package com.example.data.paging

import com.example.model.RegularEvent
import com.example.network.model.toRegularEvent
import com.example.network.retrofit.CowGroupApi

class RegularMeetingPagingSource(
    private val api: CowGroupApi,
    private val eventId: Int
) : BasePagingSource<RegularEvent>(
    pageFetcher = { page, size ->
        api.getPagingRegularMeeting(
            eventId = eventId,
            page = page,
            size = size,
        ).data.regularEventInfos.map { it.toRegularEvent() }
    }
)
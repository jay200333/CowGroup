package com.example.data.paging

import com.example.network.model.toEvent
import com.example.network.retrofit.CowGroupApi

class ParticipateEventPagingSource(
    private val api: CowGroupApi
) : BaseEventPagingSource(pageFetcher = { page, size ->
    api.getParticipateEvents(
        page,
        size
    ).data.participatingEvents.map { it.toEvent() }
})
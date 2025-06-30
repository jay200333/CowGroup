package com.example.network.model

import com.example.model.SearchRegularEvent

data class PagingSearchRegularEventResponse(
    val searchInfos: List<SearchRegularMeetingInfo>,
    val hasNext: Boolean
)

data class SearchRegularMeetingInfo(
    val eventId: Int,
    val regularId: Int,
    val name: String,
    val category: String,
    val accessUrl: String?,
    val dateTime: String,
    val capacity: Int,
    val applicants: Int,
    val eventParticipated: Boolean
)

fun SearchRegularMeetingInfo.toSearchRegularEvent(): SearchRegularEvent {
    return SearchRegularEvent(
        id = eventId,
        regularId = regularId,
        name = name,
        category = category,
        accessUrl = accessUrl ?: "",
        dateTime = dateTime,
        capacity = capacity,
        applicants = applicants,
        eventParticipated = eventParticipated
    )
}

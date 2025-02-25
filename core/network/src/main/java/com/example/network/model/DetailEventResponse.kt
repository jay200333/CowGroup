package com.example.network.model

import com.example.model.DetailEvent

data class DetailEventResponse(
    val eventId: Int,
    val name: String,
    val author: String,
    val category: String,
    val createdDate: String,
    val location: String,
    val content: String,
    val eventDate: String,
    val capacity: Int,
    val applicants: Int,
    val bookmarkStatus: String,
)

fun DetailEventResponse.toDetailEvent(): DetailEvent {
    return DetailEvent(
        id = eventId,
        name = name,
        author = author,
        category = category,
        createdDate = createdDate,
        location = location,
        content = content,
        eventDate = eventDate,
        capacity = capacity,
        applicants = applicants,
        isBookmarked = bookmarkStatus == "BOOKMARK"
    )
}
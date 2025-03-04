package com.example.network.model


import com.example.model.Event

data class NetworkEvent(
    val applicants: Int,
    val author: String,
    val capacity: Int,
    val content: String,
    val createdDate: String,
    val eventDate: String,
    val id: Int,
    val name: String,
    val bookmarkStatus: String,
)

fun NetworkEvent.toEvent(): Event {
    return Event(
        id = id,
        name = name,
        author = author,
        content = content,
        eventDate = eventDate,
        createdDate = createdDate,
        participants = applicants,
        capacities = capacity,
        isBookmarked = bookmarkStatus == "BOOKMARK"
    )
}
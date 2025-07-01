package com.example.network.model


import com.example.model.Event

data class NetworkEvent(
    val id: Int,
    val name: String,
    val content: String,
    val category: String,
    val applicants: Int,
    val capacity: Int,
    val accessUrl: String?,
    val bookmarkStatus: String,
)

fun NetworkEvent.toEvent(): Event {
    return Event(
        id = id,
        name = name,
        content = content,
        category = category,
        applicants = applicants,
        capacity = capacity,
        imageUri = accessUrl?:"",
        isBookmarked = bookmarkStatus == "BOOKMARK"
    )
}
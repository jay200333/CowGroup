package com.example.network.model


import com.example.model.Event
import com.google.gson.annotations.SerializedName

data class NetworkEvent(
    val applicants: Int,
    val author: String,
    val capacity: Int,
    val content: String,
    @SerializedName("createdDateTime")
    val createdDate: String,
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
        createdDate = createdDate,
        participants = applicants,
        capacities = capacity,
        isBookmarked = bookmarkStatus == "BOOKMARK"
    )
}
package com.example.network.model

import com.example.model.Event
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEvent(
    val eventId: Int,
    val name: String,
    val content: String,
    val eventDate: String,
    val capacity: Int,
    val applicants: Int,
    val createdDate: String,
    val isBookmarked: Boolean,
)

fun NetworkEvent.toEvent(): Event = Event(
    id = eventId,
    name = name,
    content = content,
    eventDate = eventDate,
    createdDate = createdDate,
    participants = applicants,
    capacities = capacity,
    isBookmarked = isBookmarked,
)

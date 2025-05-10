package com.example.network.model

import com.example.model.DetailEvent
import com.example.model.RegularEvent

data class DetailEventResponse(
    val eventId: Int,
    val name: String,
    val category: String,
    val content: String,
    val capacity: Int,
    val applicants: Int,
    val bookmarkStatus: String,
    val accessUrl: String?,
    val eventRegistrant: Boolean,
    val participated: Boolean,
    val regularEvents: List<RegularEventDto>,
)

data class RegularEventDto(
    val id: Int,
    val name: String,
    val location: String,
    val dateTime: String,
    val capacity: Int,
    val applicants: Int,
    val regularRegistrant: Boolean,
    val participated: Boolean
)

fun DetailEventResponse.toDetailEvent(): DetailEvent = DetailEvent(
    id = eventId,
    name = name,
    category = category,
    content = content,
    capacity = capacity,
    applicants = applicants,
    isBookmarked = bookmarkStatus == "BOOKMARK",
    url = accessUrl,
    eventRegistrant = eventRegistrant,
    isParticipated = participated,
    regularEvents = regularEvents.map { it.toRegularEvent() }
)

fun RegularEventDto.toRegularEvent(): RegularEvent = RegularEvent(
    id = id,
    name = name,
    location = location,
    dateTime = dateTime,
    capacity = capacity,
    applicants = applicants,
    isRegularRegistrant = regularRegistrant,
    isParticipated = participated
)
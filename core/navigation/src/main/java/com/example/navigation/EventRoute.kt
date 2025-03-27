package com.example.navigation

import com.example.model.CreateEvent
import kotlinx.serialization.Serializable

@Serializable
data class CreateMeetingRoute(val eventId: Int, val isEditMode: Boolean, val event: CreateEvent)

@Serializable
data class EventDetailRoute(val eventId: Int)

@Serializable
data class MemberRoute(val eventId: Int)

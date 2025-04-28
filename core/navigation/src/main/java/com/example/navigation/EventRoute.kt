package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
data class CreateMeetingRoute(val eventId: Int, val isEditMode: Boolean)

@Serializable
data class EventDetailRoute(val eventId: Int)

@Serializable
data class MemberRoute(val eventId: Int)

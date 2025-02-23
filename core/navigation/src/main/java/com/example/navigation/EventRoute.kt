package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
data object CreateMeetingRoute

@Serializable
data class EventDetailRoute(val eventId: Int)

@Serializable
data object MemberRoute

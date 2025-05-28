package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
data class CreateMeetingRoute(val eventId: Int, val isEditMode: Boolean)

@Serializable
data class EventDetailRoute(val eventId: Int)

@Serializable
data class MemberRoute(val eventId: Int)

@Serializable
data class CreateRegularMeetingRoute(val eventId: Int, val isEditMode: Boolean, val regularId: Int)

@Serializable
data class RegularMemberRoute(val regularId: Int)

@Serializable
data class FullRegularMeetingRoute(val eventId: Int)

@Serializable
data class CreatePostRoute(val eventId: Int, val postId: Int, val isEditMode: Boolean)
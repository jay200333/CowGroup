package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
data object EditProfileRoute

@Serializable
data class FullMeetingRoute(val isBookmarkPage: Boolean)

@Serializable
data object FullJoinMeetingRoute

@Serializable
data object FullBookmarkMeetingRoute

@Serializable
data object MeetingScheduleRoute

@Serializable
data object MyPostsRoute

@Serializable
data object MyCommentsRoute

@Serializable
data object UserInfoRoute
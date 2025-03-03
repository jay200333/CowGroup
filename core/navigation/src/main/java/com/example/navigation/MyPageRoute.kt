package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
data object EditProfileRoute

@Serializable
data class FullMeetingRoute(val isBookmarkPage: Boolean)

@Serializable
data object SettingRoute

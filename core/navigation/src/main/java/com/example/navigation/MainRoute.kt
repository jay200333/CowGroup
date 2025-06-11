package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRouter(
    val iconId: Int,
    val title: String,
    val route: String,
)

@Serializable
data object MainGraphRoute

@Serializable
data object HomeScreenRoute : ScreenRouter(
    R.drawable.baseline_home_filled_24,
    "홈",
    "com.example.navigation.HomeScreenRoute",
)

@Serializable
data object MapScreenRoute : ScreenRouter(
    R.drawable.baseline_chat_bubble_outline_24,
    "채팅",
    "com.example.navigation.MapScreenRoute",
)

@Serializable
data object MyPageScreenRoute : ScreenRouter(
    R.drawable.outline_person_24,
    "마이 페이지",
    "com.example.navigation.MyPageScreenRoute",
)

val bottomBarScreens = listOf(HomeScreenRoute, MapScreenRoute, MyPageScreenRoute)

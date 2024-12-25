package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRouter(val iconId: Int, val title: String)

@Serializable
data object HomeScreenRoute : ScreenRouter(
    R.drawable.baseline_home_filled_24, "홈",
)

@Serializable
data object MapScreenRoute : ScreenRouter(
    R.drawable.baseline_map_24, "지도",
)

@Serializable
data object MyPageScreenRoute :
    ScreenRouter(
        R.drawable.baseline_account_circle_24, "마이 페이지",
    )

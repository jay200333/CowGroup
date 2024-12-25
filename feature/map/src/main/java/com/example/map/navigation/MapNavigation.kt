package com.example.map.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.map.presentation.MapScreen
import com.example.navigation.MapScreenRoute

fun NavController.navigateMap() {
    navigate(MapScreenRoute)
}

fun NavGraphBuilder.mapNavGraph(
    onEventClick: () -> Unit
) {
    composable<MapScreenRoute> {
        MapScreen(
            onEventClick = onEventClick
        )
    }
}

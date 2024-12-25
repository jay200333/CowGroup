package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.presentation.CreateMeetingScreen
import com.example.home.presentation.EventDetailScreen
import com.example.home.presentation.HomeScreen
import com.example.navigation.CreateMeetingRoute
import com.example.navigation.EventDetailRoute
import com.example.navigation.HomeScreenRoute

fun NavController.navigateHome() {
    navigate(HomeScreenRoute) {
    }
}

fun NavController.navigateCreateMeeting() {
    navigate(CreateMeetingRoute)
}

fun NavController.navigateEventDetail() {
    navigate(EventDetailRoute)
}

fun NavGraphBuilder.homeNavGraph(
    onLoginButtonClick: () -> Unit,
    onEventClick: () -> Unit,
    onCreateMeetingClick: () -> Unit,
) {
    composable<HomeScreenRoute> {
        HomeScreen(
            onLoginButtonClick = onLoginButtonClick,
            onEventClick = onEventClick,
            onCreateMeetingClick = onCreateMeetingClick,
        )
    }
    composable<CreateMeetingRoute> {
        CreateMeetingScreen()
    }
    composable<EventDetailRoute> {
        EventDetailScreen()
    }
}

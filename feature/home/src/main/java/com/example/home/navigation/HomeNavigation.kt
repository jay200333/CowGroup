package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.presentation.CreateMeetingScreen
import com.example.home.presentation.EventDetailScreen
import com.example.home.presentation.HomeScreen
import com.example.home.presentation.MemberScreen
import com.example.navigation.CreateMeetingRoute
import com.example.navigation.EventDetailRoute
import com.example.navigation.HomeScreenRoute
import com.example.navigation.MemberRoute

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

fun NavController.navigateMember() {
    navigate(MemberRoute)
}

fun NavGraphBuilder.homeNavGraph(
    onLoginButtonClick: () -> Unit,
    onEventClick: () -> Unit,
    onEventNavigateButtonClick: () -> Unit,
    onCreateMeetingClick: () -> Unit,
    onMemberButtonClick: () -> Unit,
    onMemberNavigateButtonClick: () -> Unit,
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
        EventDetailScreen(
            onMemberButtonClick = onMemberButtonClick,
            onEventNavigateButtonClick = onEventNavigateButtonClick
        )
    }
    composable<MemberRoute> {
        MemberScreen(
            onMemberNavigateButtonClick = onMemberNavigateButtonClick
        )
    }
}

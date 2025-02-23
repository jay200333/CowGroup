package com.example.home.navigation

import androidx.compose.material3.SnackbarHostState
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

fun NavController.navigateEventDetail(eventId: Int) {
    navigate(EventDetailRoute(eventId))
}

fun NavController.navigateMember() {
    navigate(MemberRoute)
}

fun NavGraphBuilder.homeNavGraph(
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
    onLogoutButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    onCreateMeetingClick: () -> Unit,
    onMemberButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
    onCreateMeetingSuccess: () -> Unit,
    onEditMeetingSuccess: () -> Unit,
) {
    composable<HomeScreenRoute> {
        HomeScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onLogoutButtonClick = onLogoutButtonClick,
            onEventClick = { eventId -> onEventClick(eventId) },
            onCreateMeetingClick = onCreateMeetingClick,
        )
    }
    composable<CreateMeetingRoute> {
        CreateMeetingScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onNavigationButtonClick = onNavigationButtonClick,
            onCreateMeetingSuccess = onCreateMeetingSuccess,
            onEditMeetingSuccess = onEditMeetingSuccess,
        )
    }
    composable<EventDetailRoute> {
        EventDetailScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onMemberButtonClick = onMemberButtonClick,
            onNavigationButtonClick = onNavigationButtonClick,
        )
    }
    composable<MemberRoute> {
        MemberScreen(
            onNavigationButtonClick = onNavigationButtonClick,
        )
    }
}

package com.example.home.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.presentation.CreateMeetingScreen
import com.example.home.presentation.CreateRegularMeetingScreen
import com.example.home.presentation.EventDetailScreen
import com.example.home.presentation.HomeScreen
import com.example.home.presentation.MemberScreen
import com.example.navigation.CreateMeetingRoute
import com.example.navigation.CreateRegularMeetingRoute
import com.example.navigation.EventDetailRoute
import com.example.navigation.HomeScreenRoute
import com.example.navigation.MemberRoute

fun NavController.navigateHome() {
    navigate(HomeScreenRoute) {
    }
}

fun NavController.navigateCreateMeeting(eventId: Int, isEditMode: Boolean) {
    navigate(CreateMeetingRoute(eventId, isEditMode))
}

fun NavController.navigateEventDetail(eventId: Int) {
    navigate(EventDetailRoute(eventId))
}

fun NavController.navigateMember(eventId: Int) {
    navigate(MemberRoute(eventId))
}

fun NavController.navigateCreateRegularMeeting(eventId: Int, isEditMode: Boolean) {
    navigate(CreateRegularMeetingRoute(eventId, isEditMode))
}

fun NavGraphBuilder.homeNavGraph(
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
    onLogoutButtonClick: () -> Unit,
    onHomeScreen: () -> Unit,
    onEventClick: (Int) -> Unit,
    onCreateMeetingClick: (Int, Boolean) -> Unit,
    onMemberButtonClick: (Int) -> Unit,
    onCreateRegularMeetingClick: (Int, Boolean) -> Unit,
    onNavigationButtonClick: () -> Unit,
    onCreateMeetingSuccess: (Int) -> Unit,
    onEditMeetingSuccess: (Int) -> Unit,
    onDeleteMeetingSuccess: () -> Unit,
) {
    composable<HomeScreenRoute> {
        HomeScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onLogoutButtonClick = onLogoutButtonClick,
            onEventClick = { eventId -> onEventClick(eventId) },
            onCreateMeetingClick = { eventId, isEditMode ->
                onCreateMeetingClick(
                    eventId,
                    isEditMode
                )
            },
        )
    }
    composable<CreateMeetingRoute>
    {
        CreateMeetingScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onNavigationButtonClick = onNavigationButtonClick,
            onCreateMeetingSuccess = { eventId -> onCreateMeetingSuccess(eventId) },
            onEditMeetingSuccess = { eventId -> onEditMeetingSuccess(eventId) },
        )
    }
    composable<EventDetailRoute> {
        EventDetailScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onMemberButtonClick = { eventId -> onMemberButtonClick(eventId) },
            onNavigationButtonClick = onHomeScreen,
            onEditButtonClick = { eventId, isEditMode ->
                onCreateMeetingClick(
                    eventId,
                    isEditMode
                )
            },
            onCreateRegularMeetingButtonClick = { eventId, isEditMode ->
                onCreateRegularMeetingClick(
                    eventId,
                    isEditMode
                )
            },
            onDeleteMeetingSuccess = onDeleteMeetingSuccess
        )
    }
    composable<MemberRoute> {
        MemberScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onNavigationButtonClick = onNavigationButtonClick,
        )
    }

    composable<CreateRegularMeetingRoute> {
        CreateRegularMeetingScreen(
            onNavigationButtonClick = onNavigationButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
        )
    }
}

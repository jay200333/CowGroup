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
import com.example.home.presentation.RegularMemberScreen
import com.example.navigation.CreateMeetingRoute
import com.example.navigation.CreateRegularMeetingRoute
import com.example.navigation.EventDetailRoute
import com.example.navigation.HomeScreenRoute
import com.example.navigation.MemberRoute
import com.example.navigation.RegularMemberRoute

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

fun NavController.navigateRegularMember(regularId: Int) {
    navigate(RegularMemberRoute(regularId))
}

fun NavController.navigateCreateRegularMeeting(eventId: Int, isEditMode: Boolean, regularId: Int) {
    navigate(CreateRegularMeetingRoute(eventId, isEditMode, regularId))
}

fun NavGraphBuilder.homeNavGraph(
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
    onLogoutButtonClick: () -> Unit,
    onHomeScreen: () -> Unit,
    onEventClick: (Int) -> Unit,
    onCreateMeetingClick: (Int, Boolean) -> Unit,
    onMemberButtonClick: (Int) -> Unit,
    onRegularMemberButtonClick: (Int) -> Unit,
    onCreateRegularMeetingClick: (Int, Boolean, Int) -> Unit,
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
            onRegularMemberButtonClick = { regularId -> onRegularMemberButtonClick(regularId)},
            onNavigationButtonClick = onHomeScreen,
            onEditButtonClick = { eventId, isEditMode ->
                onCreateMeetingClick(
                    eventId,
                    isEditMode
                )
            },
            onCreateRegularMeetingButtonClick = { eventId, isEditMode, regularId ->
                onCreateRegularMeetingClick(
                    eventId,
                    isEditMode,
                    regularId
                )
            },
            onEditRegularMeetingButtonClick = { eventId, isEditMode, regularId ->
                onCreateRegularMeetingClick(
                    eventId,
                    isEditMode,
                    regularId
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
            onCreateRegularMeetingSuccess = onNavigationButtonClick,
            onEditRegularMeetingSuccess = onNavigationButtonClick,
            onNavigationButtonClick = onNavigationButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
        )
    }

    composable<RegularMemberRoute> {
        RegularMemberScreen(
            onNavigationButtonClick = onNavigationButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar
        )
    }
}

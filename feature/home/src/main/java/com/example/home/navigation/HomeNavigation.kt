package com.example.home.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.presentation.CreateMeetingScreen
import com.example.home.presentation.CreateRegularMeetingScreen
import com.example.home.presentation.EventDetailMainScreen
import com.example.home.presentation.FullRegularMeetingScreen
import com.example.home.presentation.HomeMainScreen
import com.example.home.presentation.MemberScreen
import com.example.home.presentation.RegularMemberScreen
import com.example.navigation.CreateMeetingRoute
import com.example.navigation.CreateRegularMeetingRoute
import com.example.navigation.EventDetailRoute
import com.example.navigation.FullRegularMeetingRoute
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

fun NavController.navigateFullRegularMeeting() {
    navigate(FullRegularMeetingRoute)
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
    onShowFullRegularMeetingClick: () -> Unit,
    twoClick: (Int) -> Unit
) {
    composable<HomeScreenRoute> {
        var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
        HomeMainScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it },
            onLogoutButtonClick = onLogoutButtonClick,
            onEventClick = { eventId -> onEventClick(eventId) },
            onCreateMeetingClick = { eventId, isEditMode ->
                onCreateMeetingClick(
                    eventId,
                    isEditMode
                )
            },
            twoClick = twoClick
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
        EventDetailMainScreen(
            onDeleteMeetingSuccess = onDeleteMeetingSuccess,
            onNavigationButtonClick = onNavigationButtonClick,
            onEditButtonClick = { eventId, isEditMode ->
                onCreateMeetingClick(
                    eventId,
                    isEditMode
                )
            },
            onMemberButtonClick = { eventId -> onMemberButtonClick(eventId) },
            onShowFullRegularMeetingClick = onShowFullRegularMeetingClick,
            onCreateRegularMeetingButtonClick = { eventId, isEditMode, regularId ->
                onCreateRegularMeetingClick(
                    eventId,
                    isEditMode,
                    regularId
                )
            },
            onRegularMemberButtonClick = { regularId -> onRegularMemberButtonClick(regularId) },
            onEditRegularMeetingButtonClick = { eventId, isEditMode, regularId ->
                onCreateRegularMeetingClick(
                    eventId,
                    isEditMode,
                    regularId
                )
            },
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar
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

    composable<FullRegularMeetingRoute> {
        FullRegularMeetingScreen(
            onNavigationButtonClick = onNavigationButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar
        )
    }
}
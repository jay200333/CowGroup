package com.example.home.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable

@Composable
fun HomeMainScreen(
    onLogoutButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    onCreateMeetingClick: (Int, Boolean) -> Unit,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    twoClick: (Int) -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    when (selectedTabIndex) {
        0 -> HomeExploreScreen(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
            onLogoutButtonClick = onLogoutButtonClick,
            onEventClick = onEventClick,
            onCreateMeetingClick = onCreateMeetingClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar
        )
        1 -> HomeCalendarScreen(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
            twoClick = twoClick
        )
    }
}
package com.example.mypage.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mypage.presentation.EditProfileScreen
import com.example.mypage.presentation.FullMeetingScreen
import com.example.mypage.presentation.MyPageScreen
import com.example.mypage.presentation.SettingScreen
import com.example.navigation.EditProfileRoute
import com.example.navigation.FullMeetingRoute
import com.example.navigation.MyPageScreenRoute
import com.example.navigation.SettingRoute

fun NavController.navigateMyPage() {
    navigate(MyPageScreenRoute) {
    }
}

fun NavController.navigateEditProfile() {
    navigate(EditProfileRoute)
}

fun NavController.navigateFullMeeting(isBookmarkPage: Boolean) {
    navigate(FullMeetingRoute(isBookmarkPage))
}

fun NavController.navigateSetting() {
    navigate(SettingRoute)
}

fun NavGraphBuilder.myPageNavGraph(
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
    onEventClick: (Int) -> Unit,
    onEditProfileButtonClick: () -> Unit,
    onSettingButtonClick: () -> Unit,
    onFullMeetingButtonClick: (Boolean) -> Unit,
    onNavigationButtonClick: () -> Unit,
    onEditProfileSuccess: () -> Unit,
) {
    composable<MyPageScreenRoute> {
        MyPageScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onEventClick = { eventId -> onEventClick(eventId) },
            onEditProfileButtonClick = onEditProfileButtonClick,
            onFullMeetingButtonClick = { isBookmarkPage -> onFullMeetingButtonClick(isBookmarkPage) },
            onSettingButtonClick = onSettingButtonClick,
        )
    }
    composable<EditProfileRoute> {
        EditProfileScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onNavigationButtonClick = onNavigationButtonClick,
            onEditProfileSuccess = onEditProfileSuccess,
        )
    }
    composable<SettingRoute> {
        SettingScreen(
            onNavigationButtonClick = onNavigationButtonClick,
        )
    }

    composable<FullMeetingRoute> {
        FullMeetingScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onNavigationButtonClick = onNavigationButtonClick,
            onEventClick = { eventId -> onEventClick(eventId) },
        )
    }
}

package com.example.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mypage.presentation.EditProfileScreen
import com.example.mypage.presentation.MyPageScreen
import com.example.mypage.presentation.SettingScreen
import com.example.navigation.EditProfileRoute
import com.example.navigation.MyPageScreenRoute
import com.example.navigation.SettingRoute

fun NavController.navigateMyPage() {
    navigate(MyPageScreenRoute) {
    }
}

fun NavController.navigateEditProfile() {
    navigate(EditProfileRoute)
}

fun NavController.navigateSetting() {
    navigate(SettingRoute)
}

fun NavGraphBuilder.myPageNavGraph(
    onEventClick: () -> Unit,
    onEditProfileButtonClick: () -> Unit,
    onSettingButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
) {
    composable<MyPageScreenRoute> {
        MyPageScreen(
            onEventClick = onEventClick,
            onEditProfileButtonClick = onEditProfileButtonClick,
            onSettingButtonClick = onSettingButtonClick,
        )
    }
    composable<EditProfileRoute> {
        EditProfileScreen(
            onNavigationButtonClick = onNavigationButtonClick,
        )
    }
    composable<SettingRoute> {
        SettingScreen(
            onNavigationButtonClick = onNavigationButtonClick,
        )
    }
}

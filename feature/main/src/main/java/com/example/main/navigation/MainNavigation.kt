package com.example.main.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.home.navigation.homeNavGraph
import com.example.home.navigation.navigateCreateMeeting
import com.example.home.navigation.navigateCreatePost
import com.example.home.navigation.navigateCreateRegularMeeting
import com.example.home.navigation.navigateEventDetail
import com.example.home.navigation.navigateFullRegularMeeting
import com.example.home.navigation.navigateMember
import com.example.home.navigation.navigateRegularMember
import com.example.map.navigation.mapNavGraph
import com.example.mypage.navigation.myPageNavGraph
import com.example.mypage.navigation.navigateEditProfile
import com.example.mypage.navigation.navigateFullMeeting
import com.example.mypage.navigation.navigateSetting
import com.example.navigation.HomeScreenRoute
import com.example.navigation.LoginRoute
import com.example.navigation.MainGraphRoute

fun NavGraphBuilder.mainNavGraph(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit
) {
    homeNavGraph(
        snackBarHostState = snackBarHostState,
        onShowSnackBar = onShowSnackBar,
        onLogoutButtonClick = {
            navController.navigate(LoginRoute) {
                popUpTo(MainGraphRoute) { inclusive = true }
            }
        },
        onHomeScreen = {
            navController.navigate(HomeScreenRoute) {
                popUpTo(HomeScreenRoute.route) { inclusive = false }
            }
        },
        onEventClick = navController::navigateEventDetail,
        onCreateMeetingClick = navController::navigateCreateMeeting,
        onMemberButtonClick = navController::navigateMember,
        onRegularMemberButtonClick = navController::navigateRegularMember,
        onNavigationButtonClick = navController::navigateUp,
        onCreateMeetingSuccess = navController::navigateEventDetail,
        onCreateRegularMeetingClick = navController::navigateCreateRegularMeeting,
        onEditMeetingSuccess = navController::navigateEventDetail,
        onDeleteMeetingSuccess = {
            navController.navigate(HomeScreenRoute) {
                popUpTo(HomeScreenRoute.route) { inclusive = false }
            }
        },
        onShowFullRegularMeetingClick = navController::navigateFullRegularMeeting,
        onCreatePostButtonClick = navController::navigateCreatePost
    )
    mapNavGraph(
        onEventClick = navController::navigateEventDetail,
    )
    myPageNavGraph(
        snackBarHostState = snackBarHostState,
        onShowSnackBar = onShowSnackBar,
        onEventClick = navController::navigateEventDetail,
        onEditProfileButtonClick = navController::navigateEditProfile,
        onSettingButtonClick = navController::navigateSetting,
        onFullMeetingButtonClick = navController::navigateFullMeeting,
        onNavigationButtonClick = navController::navigateUp,
        onEditProfileSuccess = navController::navigateUp,
    )
}
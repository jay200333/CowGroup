package com.example.main.component

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.home.navigation.homeNavGraph
import com.example.home.navigation.navigateCreateMeeting
import com.example.home.navigation.navigateEventDetail
import com.example.home.navigation.navigateMember
import com.example.map.navigation.mapNavGraph
import com.example.mypage.navigation.myPageNavGraph
import com.example.mypage.navigation.navigateEditProfile
import com.example.mypage.navigation.navigateSetting
import com.example.navigation.HomeScreenRoute

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier, snackBarHostState: SnackbarHostState, onShowSnackBar: (String) -> Unit) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute,
        modifier = modifier,
    ) {
        homeNavGraph(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onLogoutButtonClick = navController::navigateUp,
            onEventClick = navController::navigateEventDetail,
            onCreateMeetingClick = navController::navigateCreateMeeting,
            onMemberButtonClick = navController::navigateMember,
            onNavigationButtonClick = navController::navigateUp,
            onCreateMeetingSuccess = navController::navigateUp,
            onEditMeetingSuccess = navController::navigateUp,
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
            onNavigationButtonClick = navController::navigateUp,
        )
    }
}

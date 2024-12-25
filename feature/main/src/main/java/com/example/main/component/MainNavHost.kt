package com.example.main.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.home.navigation.homeNavGraph
import com.example.home.navigation.navigateCreateMeeting
import com.example.home.navigation.navigateEventDetail
import com.example.home.navigation.navigateMember
import com.example.login.navigation.loginNavGraph
import com.example.login.navigation.navigateLogin
import com.example.login.navigation.navigateSignUp
import com.example.map.navigation.mapNavGraph
import com.example.mypage.navigation.myPageNavGraph
import com.example.navigation.HomeScreenRoute

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute,
        modifier = modifier,
    ) {
        homeNavGraph(
            onLoginButtonClick = navController::navigateLogin,
            onEventClick = navController::navigateEventDetail,
            onEventNavigateButtonClick = navController::navigateUp,
            onCreateMeetingClick = navController::navigateCreateMeeting,
            onMemberButtonClick = navController::navigateMember,
            onMemberNavigateButtonClick = navController::navigateUp,
        )
        mapNavGraph(
            onEventClick = navController::navigateEventDetail,
        )
        myPageNavGraph()
        loginNavGraph(
            onSignUpButtonClick = navController::navigateSignUp,
        )
    }
}

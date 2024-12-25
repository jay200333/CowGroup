package com.example.main.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.home.navigation.homeNavGraph
import com.example.home.navigation.navigateCreateMeeting
import com.example.home.navigation.navigateEventDetail
import com.example.home.presentation.HomeScreen
import com.example.login.navigation.loginNavGraph
import com.example.login.navigation.navigateLogin
import com.example.main.navigation.HomeScreenRoute
import com.example.main.navigation.MapScreenRoute
import com.example.main.navigation.MyPageScreenRoute
import com.example.map.presentation.MapScreen
import com.example.mypage.presentation.MyPageScreen

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute,
        modifier = modifier,
    ) {
        composable<HomeScreenRoute> {
            HomeScreen(
                onLoginButtonClick = { navController.navigate("login") },
                onEventClick = { },
                onCreateMeetingClick = { },
            )
        }
        composable<MapScreenRoute> {
            MapScreen()
        }
        composable<MyPageScreenRoute> {
            MyPageScreen()
        }
        homeNavGraph(
            onLoginButtonClick = navController::navigateLogin,
            onEventClick = navController::navigateEventDetail,
            onCreateMeetingClick = navController::navigateCreateMeeting,
        )

        loginNavGraph()
    }
}

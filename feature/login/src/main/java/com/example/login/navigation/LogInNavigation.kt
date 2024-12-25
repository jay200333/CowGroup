package com.example.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.login.presentation.LoginScreen
import com.example.navigation.LoginRoute

fun NavController.navigateLogin() {
    navigate(LoginRoute)
}

fun NavGraphBuilder.loginNavGraph() {
    composable<LoginRoute> {
        LoginScreen()
    }
}

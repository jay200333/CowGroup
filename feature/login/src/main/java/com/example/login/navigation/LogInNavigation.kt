package com.example.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.login.presentation.LoginScreen
import com.example.login.presentation.SignUpScreen
import com.example.navigation.LoginRoute
import com.example.navigation.SignUpRoute

fun NavController.navigateLogin() {
    navigate(LoginRoute)
}

fun NavController.navigateSignUp() {
    navigate(SignUpRoute)
}

fun NavGraphBuilder.loginNavGraph(
    onSignUpButtonClick: () -> Unit,
) {
    composable<LoginRoute> {
        LoginScreen(
            onSignUpButtonClick = onSignUpButtonClick,
        )
    }

    composable<SignUpRoute> {
        SignUpScreen()
    }
}

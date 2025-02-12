package com.example.login.navigation

import androidx.compose.material3.SnackbarHostState
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
    onLoginSuccess: () -> Unit,
    onSignUpButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    composable<LoginRoute> {
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
            onSignUpButtonClick = onSignUpButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
        )
    }

    composable<SignUpRoute> {
        SignUpScreen(
            onLoginTextClick = onNavigationButtonClick,
            onSignUpSuccess = onNavigationButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
        )
    }
}

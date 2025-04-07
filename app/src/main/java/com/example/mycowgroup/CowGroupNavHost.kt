package com.example.mycowgroup

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.example.login.navigation.loginNavGraph
import com.example.login.navigation.navigateForgotPassword
import com.example.login.navigation.navigateSignUp
import com.example.login.navigation.navigateTempPasswordSent
import com.example.main.navigation.mainNavGraph
import com.example.navigation.AuthRoute
import com.example.navigation.HomeScreenRoute
import com.example.navigation.LoginRoute
import com.example.navigation.MainGraphRoute

@Composable
fun CowGroupNavHost(
    modifier: Modifier,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AuthRoute
    ) {
        navigation<AuthRoute>(startDestination = LoginRoute) {
            loginNavGraph(
                onLoginSuccess = {
                    navController.navigate(MainGraphRoute) {
                        popUpTo(AuthRoute) { inclusive = true }
                    }
                },
                onSignUpButtonClick = navController::navigateSignUp,
                onForgotPasswordButtonClick = navController::navigateForgotPassword,
                onTempPasswordSentButtonClick = navController::navigateTempPasswordSent,
                onToLoginButtonClick = {
                    navController.navigate(LoginRoute) {
                        popUpTo(AuthRoute) { inclusive = true }
                    }
                },
                onNavigationButtonClick = navController::navigateUp,
                snackBarHostState = snackBarHostState,
                onShowSnackBar = onShowSnackBar
            )
        }

        navigation<MainGraphRoute>(startDestination = HomeScreenRoute) {
            mainNavGraph(
                snackBarHostState = snackBarHostState,
                onShowSnackBar = onShowSnackBar,
                navController = navController
            )
        }
    }
}

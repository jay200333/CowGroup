package com.example.mycowgroup

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.login.navigation.loginNavGraph
import com.example.login.navigation.navigateSignUp
import com.example.main.presentation.MainScreen
import com.example.navigation.LoginRoute
import com.example.navigation.MainGraphRoute

@Composable
fun CowGroupNavHost(
    modifier: Modifier,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = LoginRoute,
    ) {
        loginNavGraph(
            onLoginSuccess = {
                navController.navigate(MainGraphRoute.toString()) {
                    popUpTo(LoginRoute) { inclusive = true }
                }
            },
            onSignUpButtonClick = navController::navigateSignUp,
            onNavigationButtonClick = navController::navigateUp,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
        )

        composable(MainGraphRoute.toString()) {
            MainScreen()
        }
    }
}

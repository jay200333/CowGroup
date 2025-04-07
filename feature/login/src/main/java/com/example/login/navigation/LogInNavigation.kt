package com.example.login.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.login.presentation.ForgotPasswordScreen
import com.example.login.presentation.LoginScreen
import com.example.login.presentation.SignUpExtraInfoScreen
import com.example.login.presentation.SignUpScreen
import com.example.login.presentation.TempPasswordSentScreen
import com.example.navigation.ForgotPasswordRoute
import com.example.navigation.LoginRoute
import com.example.navigation.SignUpExtraInfoRoute
import com.example.navigation.SignUpRoute
import com.example.navigation.TempPasswordSentRoute

fun NavController.navigateSignUp() {
    navigate(SignUpRoute)
}

fun NavController.navigateSignUpExtraInfo() {
    navigate(SignUpExtraInfoRoute)
}

fun NavController.navigateForgotPassword() {
    navigate(ForgotPasswordRoute)
}

fun NavController.navigateTempPasswordSent() {
    navigate(TempPasswordSentRoute)
}

fun NavGraphBuilder.loginNavGraph(
    onLoginSuccess: () -> Unit,
    onSignUpButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onForgotPasswordButtonClick: () -> Unit,
    onTempPasswordSentButtonClick: () -> Unit,
    onToLoginButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    composable<LoginRoute> {
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
            onSignUpButtonClick = onSignUpButtonClick,
            onForgotPasswordButtonClick = onForgotPasswordButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
        )
    }

    composable<SignUpRoute> {
        SignUpScreen(
            onNextButtonClick = onNextButtonClick,
            onNavigationButtonClick = onNavigationButtonClick,
            onSignUpSuccess = onNavigationButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
        )
    }

    composable<SignUpExtraInfoRoute> {
        SignUpExtraInfoScreen(
            onSignUpButtonClick = onToLoginButtonClick,
            onNavigationButtonClick = onNavigationButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
        )
    }

    composable<ForgotPasswordRoute> {
        ForgotPasswordScreen(
            onNavigationButtonClick = onNavigationButtonClick,
            onIssueTempPasswordButtonClick = onTempPasswordSentButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
        )
    }

    composable<TempPasswordSentRoute> {
        TempPasswordSentScreen(
            onToLoginButtonClick = onToLoginButtonClick,
            onNavigationButtonClick = onNavigationButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
        )
    }
}

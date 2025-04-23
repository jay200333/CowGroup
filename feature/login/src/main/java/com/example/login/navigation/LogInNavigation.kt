package com.example.login.navigation

import android.os.Bundle
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.example.login.presentation.ForgotPasswordScreen
import com.example.login.presentation.LoginScreen
import com.example.login.presentation.SignUpExtraInfoScreen
import com.example.login.presentation.SignUpScreen
import com.example.login.presentation.TempPasswordSentScreen
import com.example.model.SignUpStep1Info
import com.example.navigation.ForgotPasswordRoute
import com.example.navigation.LoginRoute
import com.example.navigation.SignUpExtraInfoRoute
import com.example.navigation.SignUpRoute
import com.example.navigation.TempPasswordSentRoute
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

fun NavController.navigateSignUp() {
    navigate(SignUpRoute)
}

fun NavController.navigateSignUpExtraInfo(signUpStep1Info: SignUpStep1Info) {
    navigate(SignUpExtraInfoRoute(signUpStep1Info))
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
    onNextButtonClick: (SignUpStep1Info) -> Unit,
    onForgotPasswordButtonClick: () -> Unit,
    onToIssueTempPassword: () -> Unit,
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

    composable<SignUpRoute>(
    ) {
        SignUpScreen(
            onNextButtonClick = { signUpStep1Info -> onNextButtonClick(signUpStep1Info) },
            onNavigationButtonClick = onNavigationButtonClick,
            onSignUpSuccess = onNavigationButtonClick,
        )
    }

    composable<SignUpExtraInfoRoute>(
        typeMap = mapOf(typeOf<SignUpStep1Info>() to SignUpStep1InfoType)) {
        SignUpExtraInfoScreen(
            onSignUpSuccess = onToLoginButtonClick,
            onNavigationButtonClick = onNavigationButtonClick,
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
        )
    }

    composable<ForgotPasswordRoute> {
        ForgotPasswordScreen(
            onNavigationButtonClick = onNavigationButtonClick,
            onToIssueTempPassword = onToIssueTempPassword,
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

val SignUpStep1InfoType = object : NavType<SignUpStep1Info>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): SignUpStep1Info? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): SignUpStep1Info {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: SignUpStep1Info) {
        bundle.putString(key, Json.encodeToString(SignUpStep1Info.serializer(), value))
    }

    override fun serializeAsValue(value: SignUpStep1Info): String {
        return Json.encodeToString(SignUpStep1Info.serializer(), value)
    }
}

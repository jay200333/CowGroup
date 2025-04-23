package com.example.navigation

import com.example.model.SignUpStep1Info
import kotlinx.serialization.Serializable

@Serializable
data object AuthRoute

@Serializable
data object LoginRoute

@Serializable
data object SignUpRoute

@Serializable
data class SignUpExtraInfoRoute(val signUpStep1Info: SignUpStep1Info)

@Serializable
data object ForgotPasswordRoute

@Serializable
data object TempPasswordSentRoute
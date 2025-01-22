package com.example.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val loginButtonEnabled: Boolean = false,
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _loginUIState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState())
    val loginUIState: StateFlow<LoginUIState> = _loginUIState.asStateFlow()

    fun updateEmail(email: String) {
        _loginUIState.value = _loginUIState.value.copy(
            email = email,
            loginButtonEnabled = loginCondition(_loginUIState.value),
        )
    }

    fun updatePassword(password: String) {
        _loginUIState.value = _loginUIState.value.copy(
            password = password,
            loginButtonEnabled = loginCondition(_loginUIState.value),
        )
    }

    private fun loginCondition(state: LoginUIState): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(state.email).matches() && state.password.isNotEmpty()

    fun login() {
        viewModelScope.launch {

        }
    }
}

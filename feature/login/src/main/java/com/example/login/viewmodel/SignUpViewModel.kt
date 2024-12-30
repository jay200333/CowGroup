package com.example.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.model.SignUpInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class SignUpUIState(
    val signUpInfo: SignUpInfo = SignUpInfo("", "", ""),
    val passwordConfirm: String = "",
    val showPassword: Boolean = false,
    val showPasswordConfirm: Boolean = false,
    val signUpButtonEnabled: Boolean = false,
)

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private val _signUpUIState: MutableStateFlow<SignUpUIState> = MutableStateFlow(SignUpUIState())
    val signUpUIState: StateFlow<SignUpUIState> = _signUpUIState.asStateFlow()
    private val passwordPattern =
        Regex("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+\\-=]).{8,16}$")

    fun getPasswordPattern(): Regex = passwordPattern

    fun updateNickname(nickname: String) {
        _signUpUIState.update { state ->
            val updatedSignUpInfo = state.signUpInfo.copy(nickname = nickname)
            state.copy(
                signUpInfo = updatedSignUpInfo,
                signUpButtonEnabled = signUpCondition(state.copy(signUpInfo = updatedSignUpInfo)),
            )
        }
    }

    fun updateEmail(email: String) {
        _signUpUIState.update { state ->
            val updatedSignUpInfo = state.signUpInfo.copy(email = email)
            state.copy(
                signUpInfo = updatedSignUpInfo,
                signUpButtonEnabled = signUpCondition(state.copy(signUpInfo = updatedSignUpInfo)),
            )
        }
    }

    fun updatePassword(password: String) {
        _signUpUIState.update { state ->
            val updatedSignUpInfo = state.signUpInfo.copy(password = password)
            state.copy(
                signUpInfo = updatedSignUpInfo,
                signUpButtonEnabled = signUpCondition(state.copy(signUpInfo = updatedSignUpInfo)),
            )
        }
    }

    fun updatePasswordConfirm(passwordConfirm: String) {
        _signUpUIState.update { state ->
            state.copy(
                passwordConfirm = passwordConfirm,
                signUpButtonEnabled = signUpCondition(state.copy(passwordConfirm = passwordConfirm)),
            )
        }
    }

    fun updateShowPassword(showPassword: Boolean) {
        _signUpUIState.update { state -> state.copy(showPassword = showPassword.not()) }
    }

    fun updateShowPasswordConfirm(showPasswordConfirm: Boolean) {
        _signUpUIState.update { state -> state.copy(showPasswordConfirm = showPasswordConfirm.not()) }
    }

    private fun signUpCondition(
        state: SignUpUIState,
    ): Boolean {
        val emailCondition = Patterns.EMAIL_ADDRESS.matcher(state.signUpInfo.email).matches()
        val passwordCondition =
            state.signUpInfo.password.isNotEmpty() && state.signUpInfo.password.matches(
                passwordPattern,
            )
        val passwordConfirmCondition = state.signUpInfo.password == state.passwordConfirm
        return state.signUpInfo.nickname.isNotEmpty() && emailCondition && passwordCondition && passwordConfirmCondition
    }
}

package com.example.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepository
import com.example.model.Gender
import com.example.model.SignUpInfo
import com.example.network.model.ErrorResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class SignUpUIState(
    val signUpInfo: SignUpInfo = SignUpInfo("", Gender.MALE, "", ""),
    val passwordConfirm: String = "",
    val signUpButtonEnabled: Boolean = false,
    val validateUsernameButtonEnabled: Boolean = false,
    val validateEmailButtonEnabled: Boolean = false,
    val isValidUsername: Boolean = false,
    val isValidEmail: Boolean = false,
    val isLoading: Boolean = false,
    val isSignUpSuccess: Boolean = false,
    val message: String = "",
)

@HiltViewModel
class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _signUpUIState: MutableStateFlow<SignUpUIState> = MutableStateFlow(SignUpUIState())
    val signUpUIState: StateFlow<SignUpUIState> = _signUpUIState.asStateFlow()
    private val passwordPattern =
        Regex("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+\\-=]).{8,16}$")

    fun signUp() {
        viewModelScope.launch {
            _signUpUIState.update { state ->
                state.copy(
                    isLoading = true,
                    message = "",
                )
            }
            try {
                userRepository.signUp(signUpUIState.value.signUpInfo)
                _signUpUIState.update { state ->
                    state.copy(
                        isSignUpSuccess = true,
                        isLoading = false,
                        message = "회원가입이 완료되었습니다.",
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message,
                    )
                }
            } catch (e: Exception) {
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun updateNickname(username: String) {
        _signUpUIState.update { state ->
            val updatedSignUpInfo = state.signUpInfo.copy(username = username)
            state.copy(
                signUpInfo = updatedSignUpInfo,
                validateUsernameButtonEnabled = state.signUpInfo.username.isNotEmpty(),
                signUpButtonEnabled = signUpCondition(state.copy(signUpInfo = updatedSignUpInfo)),
            )
        }
    }

    fun updateEmail(email: String) {
        _signUpUIState.update { state ->
            val updatedSignUpInfo = state.signUpInfo.copy(email = email)
            state.copy(
                signUpInfo = updatedSignUpInfo,
                validateEmailButtonEnabled = Patterns.EMAIL_ADDRESS.matcher(state.signUpInfo.email)
                    .matches(),
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

    fun updateGender(gender: Gender) {
        _signUpUIState.update { state ->
            val updatedSignUpInfo = state.signUpInfo.copy(gender = gender)
            state.copy(signUpInfo = updatedSignUpInfo)
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

    fun checkUsername() {
        viewModelScope.launch {
            _signUpUIState.update { state ->
                state.copy(
                    isLoading = true,
                    message = "",
                )
            }
            try {
                val result = userRepository.checkUsername(signUpUIState.value.signUpInfo.username)
                if (result) {
                    _signUpUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            message = "중복된 닉네임 입니다.",
                        )
                    }
                } else {
                    _signUpUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            isValidUsername = true,
                            message = "사용 가능한 닉네임 입니다.",
                        )
                    }
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "권한이 없습니다.",
                    )
                }
            } catch (e: Exception) {
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun checkEmail() {
        viewModelScope.launch {
            _signUpUIState.update { state ->
                state.copy(
                    isLoading = true,
                    message = "",
                )
            }
            try {
                val result = userRepository.checkEmail(signUpUIState.value.signUpInfo.email)
                if (result) {
                    _signUpUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            message = "중복된 이메일 입니다.",
                        )
                    }
                } else {
                    _signUpUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            isValidEmail = true,
                            message = "사용 가능한 이메일 입니다.",
                        )
                    }
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "권한이 없습니다.",
                    )
                }
            } catch (e: Exception) {
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    private fun signUpCondition(
        state: SignUpUIState,
    ): Boolean {
        val passwordCondition = state.signUpInfo.password.isNotEmpty() &&
            state.signUpInfo.password.matches(passwordPattern)
        val passwordConfirmCondition = state.signUpInfo.password == state.passwordConfirm
        return state.isValidEmail &&
            state.isValidUsername &&
            passwordCondition &&
            passwordConfirmCondition
    }

    fun validateEmail(email: String): Boolean =
        email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun validatePassword(password: String): Boolean =
        password.isEmpty() || password.matches(passwordPattern)

    fun validatePasswordConfirm(password: String, passwordConfirm: String): Boolean =
        password == passwordConfirm

    fun setMessageClear() {
        _signUpUIState.update { state ->
            state.copy(message = "")
        }
    }
}

package com.example.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepository
import com.example.model.SignUpStep1Info
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

data class SignUpStep1UIState(
    val isLoading: Boolean = false,
    val signUpInfo: SignUpStep1Info = SignUpStep1Info("", "", ""),
    val authNumber: String = "",
    val passwordConfirm: String = "",
    val nextButtonEnabled: Boolean = false,
    val isValidUsername: Boolean = false,
    val isValidEmail: Boolean = false,
    val isValidAuthNumber: Boolean = false,
    val passwordCondition: Boolean = false,
    val passwordConfirmCondition: Boolean = false,
    val isSignUpSuccess: Boolean = false,
    val usernameMessage: String = "",
    val emailMessage: String = "",
    val authNumberMessage: String = "",
    val passwordMessage: String = "",
    val passwordConfirmMessage: String = "",
)

@HiltViewModel
class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _signUpUIState: MutableStateFlow<SignUpStep1UIState> = MutableStateFlow(SignUpStep1UIState())
    val signUpUIState: StateFlow<SignUpStep1UIState> = _signUpUIState.asStateFlow()
    private val passwordPattern =
        Regex("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+\\-=]).{8,16}$")

//    fun signUp() {
//        viewModelScope.launch {
//            _signUpUIState.update { state ->
//                state.copy(
//                    isLoading = true,
//                    //message = "",
//                )
//            }
//            try {
//                userRepository.signUp(signUpUIState.value.signUpInfo)
//                _signUpUIState.update { state ->
//                    state.copy(
//                        isSignUpSuccess = true,
//                        isLoading = false,
//                        //message = "회원가입이 완료되었습니다.",
//                    )
//                }
//            } catch (e: HttpException) {
//                val response = e.response()?.errorBody()?.string()
//                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
//                _signUpUIState.update { state ->
//                    state.copy(
//                        isLoading = false,
//                        //message = errorResponse.errors.message,
//                    )
//                }
//            } catch (e: Exception) {
//                _signUpUIState.update { state ->
//                    state.copy(
//                        isLoading = false,
//                        //message = "알 수 없는 오류가 발생했습니다.",
//                    )
//                }
//            }
//        }
//    }

//    fun updateGender(gender: Gender) {
//        _signUpUIState.update { state ->
//            val updatedSignUpInfo = state.signUpInfo.copy(gender = gender)
//            state.copy(signUpInfo = updatedSignUpInfo)
//        }
//    }

    fun updateNickname(username: String) {
        _signUpUIState.update { state ->
            val updatedSignUpInfo = state.signUpInfo.copy(username = username)
            state.copy(signUpInfo = updatedSignUpInfo, usernameMessage = "")
        }
    }

    fun updateEmail(email: String) {
        _signUpUIState.update { state ->
            val updatedSignUpInfo = state.signUpInfo.copy(email = email)
            val updateState = state.copy(signUpInfo = updatedSignUpInfo)
            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
                updateState.copy(emailMessage = "이메일 형식에 맞게 입력해 주세요.")
            } else {
                updateState.copy(emailMessage = "")
            }
        }
    }

    fun updateAuthNumber(authNumber: String) {
        _signUpUIState.update { state ->
            state.copy(authNumber = authNumber)
        }
    }

    fun updatePassword(password: String) {
        _signUpUIState.update { state ->
            val (isMatch, message) = validatePasswordConfirm(password, state.passwordConfirm)
            val updatedSignUpInfo = state.signUpInfo.copy(password = password)
            var updateState = state.copy(
                passwordConfirmMessage = message,
                passwordConfirmCondition = isMatch,
                signUpInfo = updatedSignUpInfo,
            )
            updateState = if (password.isNotEmpty() && password.matches(passwordPattern)) {
                updateState.copy(passwordCondition = true, passwordMessage = "")
            } else {
                updateState.copy(
                    passwordCondition = false,
                    passwordMessage = "비밀번호는 영문, 숫자, 특수문자를 포함한 8~16자리여야 합니다."
                )
            }
            updateState.copy(nextButtonEnabled = nextButtonCondition(updateState))
        }
    }

    fun updatePasswordConfirm(passwordConfirm: String) {
        _signUpUIState.update { state ->
            val (isMatch, message) = validatePasswordConfirm(
                state.signUpInfo.password,
                passwordConfirm
            )
            val updateState = state.copy(
                passwordConfirm = passwordConfirm,
                passwordConfirmCondition = isMatch,
                passwordConfirmMessage = message,
                nextButtonEnabled = nextButtonCondition(state.copy(passwordConfirmCondition = isMatch)),
            )
            updateState
        }
    }

    fun checkUsername() {
        viewModelScope.launch {
            _signUpUIState.update { state ->
                state.copy(
                    isLoading = true,
                    usernameMessage = "",
                )
            }
            try {
                val result = userRepository.checkUsername(signUpUIState.value.signUpInfo.username)
                if (result) {
                    _signUpUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            usernameMessage = "중복된 닉네임 입니다.",
                            nextButtonEnabled = nextButtonCondition(state.copy(isValidUsername = false))
                        )
                    }
                } else {
                    _signUpUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            isValidUsername = true,
                            usernameMessage = "사용 가능한 닉네임 입니다.",
                            nextButtonEnabled = nextButtonCondition(state.copy(isValidUsername = true))
                        )
                    }
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        usernameMessage = errorResponse.errors.message,
                        nextButtonEnabled = nextButtonCondition(state.copy(isValidUsername = false))
                    )
                }
            } catch (e: Exception) {
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        usernameMessage = "알 수 없는 오류가 발생했습니다.",
                        nextButtonEnabled = nextButtonCondition(state.copy(isValidUsername = false))
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
                    emailMessage = "",
                )
            }
            try {
                val result = userRepository.checkEmail(signUpUIState.value.signUpInfo.email)
                if (result) {
                    _signUpUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            isValidEmail = false,
                            emailMessage = "중복된 이메일 입니다.",
                            nextButtonEnabled = nextButtonCondition(state.copy(isValidEmail = false))
                        )
                    }
                } else {
                    _signUpUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            isValidEmail = true,
                            emailMessage = "인증 요청 이메일이 전송되었습니다.",
                            nextButtonEnabled = nextButtonCondition(state.copy(isValidEmail = true))
                        )
                    }
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        emailMessage = errorResponse.errors.message,
                        nextButtonEnabled = nextButtonCondition(state.copy(isValidEmail = false))
                    )
                }
            } catch (e: Exception) {
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        emailMessage = "알 수 없는 오류가 발생했습니다.",
                        nextButtonEnabled = nextButtonCondition(state.copy(isValidEmail = false))
                    )
                }
            }
        }
    }

    fun checkAuthNumber() {
        viewModelScope.launch {
            _signUpUIState.update { state ->
                state.copy(
                    isLoading = true,
                    authNumberMessage = ""
                )
            }
            try {
                val result = false // 인증번호 api
                if (result) {
                    _signUpUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            authNumberMessage = "인증번호가 올바르지 않습니다.",
                            nextButtonEnabled = nextButtonCondition(state.copy(isValidAuthNumber = false))
                        )
                    }
                } else {
                    _signUpUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            isValidAuthNumber = true,
                            authNumberMessage = "이메일 인증이 완료되었습니다.",
                            nextButtonEnabled = nextButtonCondition(state.copy(isValidAuthNumber = true))
                        )
                    }
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        authNumberMessage = errorResponse.errors.message,
                        nextButtonEnabled = nextButtonCondition(state.copy(isValidAuthNumber = false))
                    )
                }
            } catch (e: Exception) {
                _signUpUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        authNumberMessage = "알 수 없는 오류가 발생했습니다.",
                        nextButtonEnabled = nextButtonCondition(state.copy(isValidAuthNumber = false))
                    )
                }
            }
        }
    }

    private fun validatePasswordConfirm(
        password: String,
        passwordConfirm: String
    ): Pair<Boolean, String> {
        return if (passwordConfirm.isNotEmpty() || password.isNotEmpty()) {
            if (password != passwordConfirm) {
                false to "비밀번호가 일치하지 않습니다."
            } else {
                true to "비밀번호가 일치합니다."
            }
        } else {
            false to ""
        }
    }

    private fun nextButtonCondition(
        state: SignUpStep1UIState,
    ): Boolean {
        return (state.isValidUsername &&
                state.isValidEmail &&
                state.isValidAuthNumber &&
                state.passwordCondition &&
                state.passwordConfirmCondition).not()
    }
}

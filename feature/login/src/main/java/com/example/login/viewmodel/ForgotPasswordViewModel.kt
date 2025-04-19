package com.example.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepository
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

data class ForgotPasswordUIState(
    val isLoading: Boolean = false,
    val sendTempPasswordSuccess: Boolean = false,
    val email: String = "",
    val isValidEmail: Boolean = false,
    val emailMessage: String = "",
    val message: String = "",
)

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _forgotPasswordUIState: MutableStateFlow<ForgotPasswordUIState> =
        MutableStateFlow(ForgotPasswordUIState())
    val forgotPasswordUIState: StateFlow<ForgotPasswordUIState> =
        _forgotPasswordUIState.asStateFlow()

    fun updateEmail(email: String) {
        _forgotPasswordUIState.update { state ->
            val updateState = state.copy(email = email)
            if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
                updateState.copy(emailMessage = "이메일 형식에 맞게 입력해 주세요.", isValidEmail = false)
            } else {
                updateState.copy(emailMessage = "", isValidEmail = true)
            }
        }
    }

    fun sendTempPassword() {
        viewModelScope.launch {
            _forgotPasswordUIState.update { state ->
                state.copy(
                    isLoading = true,
                )
            }
            try {
                userRepository.sendTempPassword(forgotPasswordUIState.value.email)
                _forgotPasswordUIState.update { state ->
                    state.copy(
                        sendTempPasswordSuccess = true,
                        isLoading = false,
                        message = "임시 비밀번호가 전송되었습니다."
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _forgotPasswordUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message,
                    )
                }
            } catch (e: Exception) {
                _forgotPasswordUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun setMessageClear() {
        _forgotPasswordUIState.update { state ->
            state.copy(message = "")
        }
    }
}
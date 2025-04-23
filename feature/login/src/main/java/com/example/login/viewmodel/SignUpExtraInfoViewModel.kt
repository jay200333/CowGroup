package com.example.login.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepository
import com.example.model.Gender
import com.example.model.MBTI
import com.example.model.SignUpInfo
import com.example.model.SignUpStep1Info
import com.example.network.model.ErrorResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject

data class SignUpUIState(
    val isLoading: Boolean = false,
    val signUpInfo: SignUpInfo = SignUpInfo("", "", "", "", Gender.MALE, MBTI.INTJ),
    val isSignUpSuccess: Boolean = false,
    val message: String = ""
)

@HiltViewModel
class SignUpExtraInfoViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _signUpUIState: MutableStateFlow<SignUpUIState> = MutableStateFlow(SignUpUIState())
    val signUpUIState: StateFlow<SignUpUIState> = _signUpUIState.asStateFlow()
    private val signUpStep1Info: SignUpStep1Info = requireNotNull(
        savedStateHandle.get<String>("signUpStep1Info")
            ?.let { string -> Json.decodeFromString<SignUpStep1Info>(string) }) { "event is required." }

    init {
        initSignUpInfo()
    }

    private fun initSignUpInfo() {
        _signUpUIState.update { state ->
            val updatedSignUpInfo = state.signUpInfo.copy(
                username = signUpStep1Info.username,
                email = signUpStep1Info.email,
                code = signUpStep1Info.authCode,
                password = signUpStep1Info.password
            )
            state.copy(signUpInfo = updatedSignUpInfo)
        }
    }

    fun signUp() {
        viewModelScope.launch {
            _signUpUIState.update { state ->
                state.copy(
                    isLoading = true,
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

    fun updateGender(gender: Gender) {
        _signUpUIState.update { state ->
            val updatedSignUpInfo = state.signUpInfo.copy(gender = gender)
            state.copy(signUpInfo = updatedSignUpInfo)
        }
    }

    fun updateMBTI(mbti: MBTI) {
        _signUpUIState.update { state ->
            val updatedSignUpInfo = state.signUpInfo.copy(mbti = mbti)
            state.copy(signUpInfo = updatedSignUpInfo)
        }
    }

    fun setMessageClear() {
        _signUpUIState.update { state ->
            state.copy(message = "")
        }
    }
}
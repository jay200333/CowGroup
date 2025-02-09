package com.example.login.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepository
import com.example.datastore.TokenDataStore
import com.example.model.LoginInfo
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

data class LoginUIState(
    val loginInfo: LoginInfo = LoginInfo(email = "", password = ""),
    val loginButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val message: String = "",
)

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository, private val tokenDataStore: TokenDataStore) : ViewModel() {
    private val _loginUIState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState())
    val loginUIState: StateFlow<LoginUIState> = _loginUIState.asStateFlow()

    fun updateEmail(email: String) {
        _loginUIState.update { state ->
            val updatedLoginInfo = state.loginInfo.copy(email = email)
            state.copy(
                loginInfo = updatedLoginInfo,
                loginButtonEnabled = loginCondition(state.copy(loginInfo = updatedLoginInfo)),
            )
        }
    }

    fun updatePassword(password: String) {
        _loginUIState.update { state ->
            val updatedLoginInfo = state.loginInfo.copy(password = password)
            state.copy(
                loginInfo = updatedLoginInfo,
                loginButtonEnabled = loginCondition(state.copy(loginInfo = updatedLoginInfo)),
            )
        }
    }

    private fun loginCondition(state: LoginUIState): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(state.loginInfo.email)
            .matches() && state.loginInfo.password.isNotEmpty()

    fun login() {
        viewModelScope.launch {
            _loginUIState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                val token = userRepository.login(loginUIState.value.loginInfo)
                if (!token.isNullOrEmpty()) {
                    tokenDataStore.saveToken(token)
                    _loginUIState.update { state -> state.copy(isLoading = false, message = "", isLoginSuccess = true) }
                } else {
                    _loginUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            message = "로그인에 실패했습니다.",
                        )
                    }
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                Log.d("loginResult", "$response")
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                Log.d("loginResult", "$errorResponse.errors.message")
                _loginUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message,
                    )
                }
            } catch (e: Exception) {
                _loginUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }
}

package com.example.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepository
import com.example.datastore.CowGroupDataStore
import com.example.model.LoginInfo
import com.example.network.model.ErrorResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenDataStore: CowGroupDataStore,
) : ViewModel() {
    private val _loginUIState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState())
    val loginUIState: StateFlow<LoginUIState> = _loginUIState.onStart {
        checkLogin()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        LoginUIState(),
    )

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
                    _loginUIState.update { state ->
                        state.copy(
                            isLoading = false,
                            message = "",
                            isLoginSuccess = true,
                        )
                    }
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
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
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

    private fun checkLogin() {
        viewModelScope.launch {
            val isLogin = tokenDataStore.loginCheck()
            _loginUIState.update { state ->
                state.copy(isLoginSuccess = isLogin)
            }
        }
    }

    fun setMessageClear() {
        _loginUIState.update { state ->
            state.copy(message = "")
        }
    }
}

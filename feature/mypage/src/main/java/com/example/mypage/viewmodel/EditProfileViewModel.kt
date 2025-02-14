package com.example.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.UserRepository
import com.example.model.Profile
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

data class EditProfileUIState(
    val isLoading: Boolean = false,
    val editButtonEnabled: Boolean = false,
    val isEditProfileSuccess: Boolean = false,
    val message: String = "",
    val profile: Profile = Profile(
        username = "",
        introduction = "",
        localName = "",
        birth = "",
        mbti = "",
    )
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _editProfileUIState: MutableStateFlow<EditProfileUIState> =
        MutableStateFlow(EditProfileUIState())
    val editProfileUIState: StateFlow<EditProfileUIState> = _editProfileUIState.asStateFlow()

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            _editProfileUIState.update { state ->
                state.copy(isLoading = true)
            }
            try {
                val result = userRepository.getUserInfo()
                _editProfileUIState.update { state ->
                    state.copy(profile = result)
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _editProfileUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "error",
                    )
                }
            } catch (e: Exception) {
                _editProfileUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun updateMBTI(mbti: String) {
        _editProfileUIState.update { state ->
            state.copy(profile = state.profile.copy(mbti = mbti))
        }
    }

    fun setMessageClear() {
        _editProfileUIState.update { state ->
            state.copy(message = "")
        }
    }
}
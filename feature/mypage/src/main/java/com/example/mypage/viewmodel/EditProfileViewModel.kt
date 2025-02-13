package com.example.mypage.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
class EditProfileViewModel @Inject constructor(): ViewModel() {
    private val _editProfileUIState: MutableStateFlow<EditProfileUIState> = MutableStateFlow(EditProfileUIState())
    val editProfileUIState: StateFlow<EditProfileUIState> = _editProfileUIState.asStateFlow()

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
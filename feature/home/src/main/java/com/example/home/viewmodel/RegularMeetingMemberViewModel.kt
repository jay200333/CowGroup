package com.example.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.data.repository.RegularMeetingRepository
import com.example.model.EventMember
import com.example.navigation.RegularMemberRoute
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

data class RegularMeetingMemberUIState(
    val isLoading: Boolean = false,
    val regularMeetingMember: EventMember = EventMember(
        memberInfoList = emptyList(),
        memberCount = 0
    ),
    val message: String = "",
)

@HiltViewModel
class RegularMeetingMemberViewModel @Inject constructor(
    private val regularMeetingRepository: RegularMeetingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val regularId: Int = savedStateHandle.toRoute<RegularMemberRoute>().regularId
    private val _regularMemberUIState: MutableStateFlow<RegularMeetingMemberUIState> = MutableStateFlow(RegularMeetingMemberUIState())
    val regularMemberUIState: StateFlow<RegularMeetingMemberUIState> = _regularMemberUIState.asStateFlow()

    init {
        getMemberList()
    }

    fun getMemberList() {
        viewModelScope.launch {
            _regularMemberUIState.value = RegularMeetingMemberUIState(isLoading = true)
            try {
                val memberList = regularMeetingRepository.getRegularMemberList(regularId)
                _regularMemberUIState.value = RegularMeetingMemberUIState(
                    isLoading = false,
                    regularMeetingMember = memberList
                )
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _regularMemberUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message
                    )
                }
            } catch (e: Exception) {
                _regularMemberUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다."
                    )
                }
            }
        }
    }

    fun setMessageClear() {
        _regularMemberUIState.update { state ->
            state.copy(message = "")
        }
    }
}
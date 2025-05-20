package com.example.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RegularMeetingRepository
import com.example.model.RegularEvent
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

data class FullRegularMeetingUIState(
    val isLoading: Boolean = false,
    val regularMeetingList: List<RegularEvent> = emptyList(),
    val message: String = "",
)


@HiltViewModel
class FullRegularMeetingViewModel @Inject constructor(
    private val regularMeetingRepository: RegularMeetingRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<FullRegularMeetingUIState> =
        MutableStateFlow(FullRegularMeetingUIState())
    val uiState: StateFlow<FullRegularMeetingUIState> = _uiState.asStateFlow()

    init {
        getRegularMeeting()
    }

    // 테스트용
    fun getRegularMeeting() {
        _uiState.update { state ->
            state.copy(
                isLoading = true,
                regularMeetingList = listOf(
                    RegularEvent(
                        id = 6,
                        name = "regularTest",
                        "korea",
                        "2025-05-30T12:32:00",
                        12,
                        4,
                        true,
                        true
                    ),
                    RegularEvent(
                        id = 7,
                        name = "regularTest2",
                        "korea",
                        "2025-05-29T12:38:00",
                        14,
                        12,
                        true,
                        true
                    ),
                    RegularEvent(
                        id = 8,
                        name = "123",
                        "12",
                        "2025-05-24T13:43:00",
                        12,
                        2,
                        true,
                        true
                    ),
                )
            )
        }
    }

    fun updateJoinRegularMeeting(regularEventId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            try {
                val currentState = _uiState.value
                val regularEvent =
                    currentState.regularMeetingList.find { it.id == regularEventId }
                if (regularEvent != null) {
                    regularMeetingRepository.updateJoinRegularMeeting(
                        regularEventId,
                        regularEvent.isParticipated
                    )

                    val updatedRegularEvents = currentState.regularMeetingList.map { event ->
                        if (event.id == regularEventId) {
                            event.copy(isParticipated = !event.isParticipated)
                        } else {
                            event
                        }
                    }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            regularMeetingList = updatedRegularEvents
                        )
                    }
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = errorResponse.errors.message
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, message = "알 수 없는 오류가 발생했습니다.")
                }
            }
        }
    }

    fun setMessageClear() {
        _uiState.update { state ->
            state.copy(message = "")
        }
    }
}
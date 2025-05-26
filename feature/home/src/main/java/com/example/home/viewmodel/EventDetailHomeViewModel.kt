package com.example.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.EventRepository
import com.example.data.repository.RegularMeetingRepository
import com.example.model.DetailEvent
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

data class EventDetailHomeUIState(
    val isLoading: Boolean = false,
    val detailEvent: DetailEvent = DetailEvent(
        id = 0,
        name = "",
        category = "",
        content = "",
        capacity = 0,
        applicants = 0,
        isBookmarked = false,
        url = "",
        eventRegistrant = false,
        isParticipated = false,
        regularEvents = emptyList()
    ),
    val message: String = ""
)

@HiltViewModel
class EventDetailHomeViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val regularMeetingRepository: RegularMeetingRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<EventDetailHomeUIState> =
        MutableStateFlow(EventDetailHomeUIState())
    val uiState: StateFlow<EventDetailHomeUIState> = _uiState.asStateFlow()

    fun getEventDetail(eventId: Int) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                val detailEvent = eventRepository.getEventDetail(eventId)
                _uiState.update { state ->
                    state.copy(
                        detailEvent = detailEvent,
                        isLoading = false
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다."
                    )
                }
            }
        }
    }

    fun updateJoinEvent(eventId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            try {
                eventRepository.updateJoinEvent(
                    eventId,
                    _uiState.value.detailEvent.isParticipated
                )
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        detailEvent = it.detailEvent.copy(isParticipated = it.detailEvent.isParticipated.not()),
                        message = "모임 참석 여부가 업데이트 되었습니다."
                    )
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

    fun updateJoinRegularMeeting(regularEventId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            try {
                val currentState = _uiState.value
                val regularEvent =
                    currentState.detailEvent.regularEvents.find { it.id == regularEventId }
                if (regularEvent != null) {
                    regularMeetingRepository.updateJoinRegularMeeting(
                        regularEvent.id,
                        regularEvent.isParticipated
                    )

                    val updatedRegularEvents = currentState.detailEvent.regularEvents.map { event ->
                        if (event.id == regularEventId) {
                            event.copy(isParticipated = !event.isParticipated)
                        } else {
                            event
                        }
                    }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            detailEvent = it.detailEvent.copy(regularEvents = updatedRegularEvents)
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

    fun deleteRegularMeeting(regularId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            try {
                regularMeetingRepository.deleteRegularMeeting(regularId)
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
}
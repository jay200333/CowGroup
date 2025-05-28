package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.data.repository.RegularMeetingRepository
import com.example.model.RegularEvent
import com.example.navigation.EventDetailRoute
import com.example.network.model.ErrorResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class FullRegularMeetingUIState(
    val isLoading: Boolean = false,
    val regularMeetingList: PagingData<RegularEvent> = PagingData.empty(),
    val message: String = "",
)

@HiltViewModel
class FullRegularMeetingViewModel @Inject constructor(
    private val regularMeetingRepository: RegularMeetingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val eventId: Int = savedStateHandle.toRoute<EventDetailRoute>().eventId
    private val _uiState: MutableStateFlow<FullRegularMeetingUIState> = MutableStateFlow(FullRegularMeetingUIState())
    val uiState: StateFlow<FullRegularMeetingUIState> = _uiState.asStateFlow()
    private val pagingRegularMeeting: Flow<PagingData<RegularEvent>> =
        regularMeetingRepository.getPagingRegularEvents(pageSize = 10, eventId = eventId).cachedIn(viewModelScope)

    init {
        pagingRegularMeeting.onEach { pagingMeeting ->
            _uiState.update {
                it.copy(
                    isLoading = false,
                    regularMeetingList = pagingMeeting
                )
            }
        }.launchIn(viewModelScope)
    }

    fun getEventId(): Int = eventId

    fun updateJoinRegularMeeting(regularEventId: Int, participationId: Int?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            try {
                val currentState = _uiState.value
                val result = regularMeetingRepository.updateJoinRegularMeeting(
                    regularEventId,
                    participationId
                )
                val updatedRegularEvents = currentState.regularMeetingList.map { event ->
                    if (event.id == regularEventId) {
                        if (result != 0) {
                            event.copy(applicants = event.applicants.plus(1), participationId = result)
                        } else {
                            event.copy(applicants = event.applicants.minus(1), participationId = result)
                        }
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
                Log.d("123123", "${e.message}")
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
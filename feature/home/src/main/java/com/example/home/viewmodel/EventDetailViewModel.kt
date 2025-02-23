package com.example.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.data.repository.EventRepository
import com.example.model.DetailEvent
import com.example.navigation.EventDetailRoute
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

data class EventDetailUIState(
    val isLoading: Boolean = false,
    val isLogout: Boolean = false,
    val detailEvent: DetailEvent = DetailEvent(
        id = 0,
        name = "",
        author = "",
        category = "",
        createdDate = "",
        address = "",
        location = "",
        content = "",
        eventDate = "",
        capacity = 0,
        applicants = 0,
        isBookmarked = false
    ),
    val message: String = "",
)

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val eventId: Int = savedStateHandle.toRoute<EventDetailRoute>().eventId
    private val _eventDetailUIState: MutableStateFlow<EventDetailUIState> =
        MutableStateFlow(EventDetailUIState())
    val eventDetailUIState: StateFlow<EventDetailUIState> = _eventDetailUIState.asStateFlow()

    init {
        getEventDetail()
    }

    private fun getEventDetail() {
        viewModelScope.launch {
            _eventDetailUIState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                eventRepository.getEventDetail(eventId)
                    .collect { detailEvent ->
                        _eventDetailUIState.update { state ->
                            state.copy(
                                detailEvent = detailEvent,
                                isLoading = false
                            )
                        }
                    }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _eventDetailUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "모임 상세 조회에 실패했습니다." //errorResponse.errors.message
                    )
                }
            } catch (e: Exception) {
                _eventDetailUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다."
                    )
                }
            }
        }
    }

    fun joinEvent() {
        viewModelScope.launch {
            _eventDetailUIState.update { it.copy(isLoading = true, message = "") }
            try {
                eventRepository.joinEvent(eventId)
                _eventDetailUIState.update {
                    it.copy(
                        isLoading = false,
                        message = "모임에 참여 되었습니다."
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _eventDetailUIState.update {
                    it.copy(
                        isLoading = false,
                        message = "참여에 실패했습니다." //errorResponse.errors.message
                    )
                }
            } catch (e: Exception) {
                _eventDetailUIState.update {
                    it.copy(isLoading = false, message = "알 수 없는 오류가 발생했습니다.")
                }
            }
        }
    }

    fun updateBookmark(isBookmarked: Boolean) {
        viewModelScope.launch {
            _eventDetailUIState.update { it.copy(isLoading = true, message = "") }
            try {
                eventRepository.updateBookmark(eventId, isBookmarked)
                _eventDetailUIState.update {
                    it.copy(
                        isLoading = false,
                        message = "북마크가 업데이트 되었습니다.",
                        detailEvent = it.detailEvent.copy(isBookmarked = isBookmarked.not())
                    )
                }
            } catch (e: HttpException) {
                _eventDetailUIState.update {
                    it.copy(
                        isLoading = false,
                        message = "북마크 업데이트에 실패했습니다."
                    )
                }
            } catch (e: Exception) {
                _eventDetailUIState.update {
                    it.copy(isLoading = false, message = "알 수 없는 오류가 발생했습니다.")
                }
            }
        }
    }

    fun setMessageClear() {
        _eventDetailUIState.update { state ->
            state.copy(message = "")
        }
    }
}
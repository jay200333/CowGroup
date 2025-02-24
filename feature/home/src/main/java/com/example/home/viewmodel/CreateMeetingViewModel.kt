package com.example.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.EventRepository
import com.example.model.CreateEvent
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

data class CreateMeetingUIState(
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val createButtonEnabled: Boolean = false,
    val isCreateMeetingSuccess: Boolean = false,
    val isEditMeetingSuccess: Boolean = false,
    val createEvent: CreateEvent = CreateEvent(
        name = "",
        category = "",
        location = "",
        eventDate = "",
        capacity = 0,
        content = "",
    ),
    val message: String = "",
)

@HiltViewModel
class CreateMeetingViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _createMeetingUIState: MutableStateFlow<CreateMeetingUIState> =
        MutableStateFlow(CreateMeetingUIState())
    val createMeetingUIState: StateFlow<CreateMeetingUIState> = _createMeetingUIState.asStateFlow()
    private val eventId: Int =
        requireNotNull(savedStateHandle.get<Int>("eventId")) { "eventId is required." }
    private val isEditMode: Boolean =
        requireNotNull(savedStateHandle.get<Boolean>("isEditMode")) { "isEditMode is required." }
    private val event: CreateEvent = requireNotNull(
        savedStateHandle.get<String>("event")
            ?.let { string -> Json.decodeFromString<CreateEvent>(string) }) { "event is required." }

    init {
        checkEditMode()
    }

    private fun checkEditMode() {
        _createMeetingUIState.update { state ->
            state.copy(isEditMode = isEditMode)
        }
        if (isEditMode) {
            _createMeetingUIState.update { state ->
                state.copy(createEvent = event)
            }
        }
    }

    fun createMeeting() {
        viewModelScope.launch {
            _createMeetingUIState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                eventRepository.createMeeting(createMeetingUIState.value.createEvent)
                _createMeetingUIState.update { state ->
                    state.copy(
                        isCreateMeetingSuccess = true,
                        isLoading = false,
                        message = "모임 생성이 완료되었습니다.",
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _createMeetingUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "모임 생성이 실패하였습니다."//errorResponse.errors.message,
                    )
                }
            } catch (e: Exception) {
                _createMeetingUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun editMeeting() {
        viewModelScope.launch {
            _createMeetingUIState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                eventRepository.editEvent(eventId, createMeetingUIState.value.createEvent)
                _createMeetingUIState.update { state ->
                    state.copy(
                        isCreateMeetingSuccess = true,
                        isLoading = false,
                        message = "모임 수정이 완료되었습니다.",
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _createMeetingUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "모임 수정이 실패하였습니다."//errorResponse.errors.message,
                    )
                }
            } catch (e: Exception) {
                _createMeetingUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun updateName(name: String) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.createEvent.copy(name = name)
            state.copy(
                createEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(createEvent = updatedDetailEvent)),
            )
        }
    }

    fun updateCategory(category: String) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.createEvent.copy(category = category)
            state.copy(
                createEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(createEvent = updatedDetailEvent)),
            )
        }
    }

    fun updateLocation(location: String) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.createEvent.copy(location = location)
            state.copy(
                createEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(createEvent = updatedDetailEvent)),
            )
        }
    }

    fun updateEventDate(eventDate: String) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.createEvent.copy(eventDate = eventDate)
            state.copy(
                createEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(createEvent = updatedDetailEvent)),
            )
        }
    }

    fun updateCapacity(capacity: Float) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.createEvent.copy(capacity = capacity.toInt())
            state.copy(
                createEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(createEvent = updatedDetailEvent)),
            )
        }
    }

    fun updateContent(content: String) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.createEvent.copy(content = content)
            state.copy(
                createEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(createEvent = updatedDetailEvent)),
            )
        }
    }

    fun setMessageClear() {
        _createMeetingUIState.update { state ->
            state.copy(message = "")
        }
    }

    private fun createMeetingCondition(
        state: CreateMeetingUIState,
    ): Boolean = state.createEvent.name.isNotEmpty() &&
            state.createEvent.category.isNotEmpty() &&
            state.createEvent.location.isNotEmpty() &&
            state.createEvent.eventDate.isNotEmpty() &&
            (state.createEvent.capacity > 0) &&
            state.createEvent.content.isNotEmpty()
}

package com.example.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.EventRepository
import com.example.model.DetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateMeetingUIState(
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val createButtonEnabled: Boolean = false,
    val isCreateMeetingSuccess: Boolean = false,
    val isEditMeetingSuccess: Boolean = false,
    val detailEvent: DetailEvent = DetailEvent(
        name = "",
        category = "",
        location = "",
        eventDate = "",
        capacity = 0,
        content = "",
    ),
    val error: String = "",
)

@HiltViewModel
class CreateMeetingViewModel @Inject constructor(
    private val eventRepository: EventRepository,
) : ViewModel() {
    private val _createMeetingUIState: MutableStateFlow<CreateMeetingUIState> =
        MutableStateFlow(CreateMeetingUIState())
    val createMeetingUIState: StateFlow<CreateMeetingUIState> = _createMeetingUIState.asStateFlow()

    fun createMeeting() {
        viewModelScope.launch {
            _createMeetingUIState.value = _createMeetingUIState.value.copy(isLoading = true, error = "")
            try {
                _createMeetingUIState.value = _createMeetingUIState.value.copy(
                    isLoading = false,
                    isCreateMeetingSuccess = true,
                )
            } catch (e: Exception) {
                _createMeetingUIState.value = _createMeetingUIState.value.copy(
                    isLoading = false,
                    error = "알 수 없는 오류가 발생했습니다.",
                )
            }
        }
    }

    fun editMeeting() {
        viewModelScope.launch {
            _createMeetingUIState.value = _createMeetingUIState.value.copy(isLoading = true, error = "")
            try {
                _createMeetingUIState.value = _createMeetingUIState.value.copy(
                    isLoading = false,
                    isEditMeetingSuccess = true,
                )
            } catch (e: Exception) {
                _createMeetingUIState.value = _createMeetingUIState.value.copy(
                    isLoading = false,
                    error = "알 수 없는 오류가 발생했습니다.",
                )
            }
        }
    }

    fun updateName(name: String) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.detailEvent.copy(name = name)
            state.copy(
                detailEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(detailEvent = updatedDetailEvent)),
            )
        }
    }

    fun updateCategory(category: String) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.detailEvent.copy(category = category)
            state.copy(
                detailEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(detailEvent = updatedDetailEvent)),
            )
        }
    }

    fun updateLocation(location: String) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.detailEvent.copy(location = location)
            state.copy(
                detailEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(detailEvent = updatedDetailEvent)),
            )
        }
    }

    fun updateEventDate(eventDate: String) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.detailEvent.copy(eventDate = eventDate)
            state.copy(
                detailEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(detailEvent = updatedDetailEvent)),
            )
        }
    }

    fun updateCapacity(capacity: Float) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.detailEvent.copy(capacity = capacity.toInt())
            state.copy(
                detailEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(detailEvent = updatedDetailEvent)),
            )
        }
    }

    fun updateContent(content: String) {
        _createMeetingUIState.update { state ->
            val updatedDetailEvent = state.detailEvent.copy(content = content)
            state.copy(
                detailEvent = updatedDetailEvent,
                createButtonEnabled = createMeetingCondition(state.copy(detailEvent = updatedDetailEvent)),
            )
        }
    }

    private fun createMeetingCondition(
        state: CreateMeetingUIState,
    ): Boolean = state.detailEvent.name.isNotEmpty() &&
        state.detailEvent.category.isNotEmpty() &&
        state.detailEvent.location.isNotEmpty() &&
        state.detailEvent.eventDate.isNotEmpty() &&
        (state.detailEvent.capacity > 0) &&
        state.detailEvent.content.isNotEmpty()
}

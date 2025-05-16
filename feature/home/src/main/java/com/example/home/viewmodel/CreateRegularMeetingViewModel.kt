package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.common.DateUtil
import com.example.model.CreateRegularMeeting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class CreateRegularMeetingUIState(
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val regularMeeting: CreateRegularMeeting = CreateRegularMeeting(
        name = "",
        dateTime = "",
        location = "",
        capacity = 0
    ),
    val createButtonEnabled: Boolean = false,
    val isCreateMeetingSuccess: Boolean = false,
    val regularMeetingDate: String = "",
    val regularMeetingTime: String = "",
    val dateTime: String = "",
    val eventId: Int = 0,
    val isValidCapacity: Boolean = false,
    val capacityMessage: String = "",
    val message: String = ""
)

@HiltViewModel
class CreateRegularMeetingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _createRegularMeetingUIState: MutableStateFlow<CreateRegularMeetingUIState> =
        MutableStateFlow(CreateRegularMeetingUIState())
    val createRegularMeetingUIState: StateFlow<CreateRegularMeetingUIState> =
        _createRegularMeetingUIState.asStateFlow()
    private val eventId: Int =
        requireNotNull(savedStateHandle.get<Int>("eventId")) { "eventId is required." }
    private val isEditMode: Boolean =
        requireNotNull(savedStateHandle.get<Boolean>("isEditMode")) { "isEditMode is required." }

    init {
        Log.d("CreateRegularViewModel", "${eventId}, $isEditMode")
    }

    fun setRegularMeetingDate(date: String) {
        _createRegularMeetingUIState.update { state ->
            state.copy(regularMeetingDate = date)
        }
        setDateTime(
            _createRegularMeetingUIState.value.regularMeetingDate,
            _createRegularMeetingUIState.value.regularMeetingTime
        )
    }

    fun setRegularMeetingTime(time: String) {
        _createRegularMeetingUIState.update { state ->
            state.copy(regularMeetingTime = time)
        }
        setDateTime(
            _createRegularMeetingUIState.value.regularMeetingDate,
            _createRegularMeetingUIState.value.regularMeetingTime
        )
    }

    fun setDateTime(date: String, time: String) {
        if (date.isNotEmpty() && time.isNotEmpty()) {
            val dateTime = DateUtil.formatDateTimeToIso8601(
                _createRegularMeetingUIState.value.regularMeetingDate,
                _createRegularMeetingUIState.value.regularMeetingTime
            )
            _createRegularMeetingUIState.update { state ->
                val regularMeeting = state.regularMeeting.copy(dateTime = dateTime)
                state.copy(regularMeeting = regularMeeting)
            }
        }
    }

    fun updateName(name: String) {
        _createRegularMeetingUIState.update { state ->
            val updatedRegularMeeting = state.regularMeeting.copy(name = name)
            state.copy(
                regularMeeting = updatedRegularMeeting,
                createButtonEnabled = createRegularMeetingCondition(state.copy(regularMeeting = updatedRegularMeeting)),
            )
        }
    }

    fun updateLocation(location: String) {
        _createRegularMeetingUIState.update { state ->
            val updatedRegularMeeting = state.regularMeeting.copy(location = location)
            state.copy(
                regularMeeting = updatedRegularMeeting,
                createButtonEnabled = createRegularMeetingCondition(state.copy(regularMeeting = updatedRegularMeeting))
            )
        }
    }

    fun updateCapacity(capacity: String) {
        _createRegularMeetingUIState.update { state ->
            val updatedRegularMeeting = if (capacity.isEmpty()) {
                state.regularMeeting.copy(capacity = 0)
            } else state.regularMeeting.copy(capacity = capacity.toInt())
            val isCapacityValid = (capacity.toIntOrNull() ?: 0) in 1..100
            val capacityMessage = if (isCapacityValid) "" else "모임원 수는 최대 100명입니다."

            state.copy(
                regularMeeting = updatedRegularMeeting,
                createButtonEnabled = createRegularMeetingCondition(
                    state.copy(
                        regularMeeting = updatedRegularMeeting,
                        capacityMessage = capacityMessage,
                        isValidCapacity = isCapacityValid
                    )
                ),
                capacityMessage = capacityMessage,
                isValidCapacity = isCapacityValid
            )
        }
    }

    private fun createRegularMeetingCondition(
        state: CreateRegularMeetingUIState
    ): Boolean =
        state.regularMeeting.name.isNotEmpty() &&
                state.regularMeeting.dateTime.isNotEmpty() &&
                state.regularMeeting.location.isNotEmpty() &&
                (state.regularMeeting.capacity > 0)

    fun setMessageClear() {
        _createRegularMeetingUIState.update { state ->
            state.copy(message = "")
        }
    }
}
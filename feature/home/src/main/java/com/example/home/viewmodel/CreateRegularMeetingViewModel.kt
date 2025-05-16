package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class CreateRegularMeetingUIState(
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val createButtonEnabled: Boolean = false,
    val isCreateMeetingSuccess: Boolean = false,
    val regularMeetingDate: String = "",
    val eventId: Int = 0,
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
    }

    fun setMessageClear() {
        _createRegularMeetingUIState.update { state ->
            state.copy(message = "")
        }
    }
}
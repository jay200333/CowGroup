package com.example.mypage.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.navigation.FullMeetingRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class FullMeetingUIState(
    val isLoading: Boolean = false,
    val message: String = "",
    val isBookmarkPage: Boolean = false,
)

@HiltViewModel
class FullMeetingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _fullMeetingUIState: MutableStateFlow<FullMeetingUIState> = MutableStateFlow(FullMeetingUIState())
    val fullMeetingUIState: StateFlow<FullMeetingUIState> = _fullMeetingUIState.asStateFlow()
    private val isBookmarkPage: Boolean = savedStateHandle.toRoute<FullMeetingRoute>().isBookmarkPage

    init {
        _fullMeetingUIState.update { state -> state.copy(isBookmarkPage = isBookmarkPage) }
    }

    fun setMessageClear() {
        _fullMeetingUIState.update { state ->
            state.copy(message = "")
        }
    }
}
package com.example.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class JoinMeetingUIState(
    val isLoading: Boolean = false,
    val eventList: List<Event> = emptyList()
)

@HiltViewModel
class JoinMeetingViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<JoinMeetingUIState> =
        MutableStateFlow(JoinMeetingUIState())
    val uiState: StateFlow<JoinMeetingUIState> = _uiState.asStateFlow()

    init {
        getEventList()
    }

    private fun getEventList() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val eventList = listOf(
                    Event(
                        id = 922,
                        name = "풋살 모임",
                        content = "풋살 모임입니다.",
                        category = "운동",
                        applicants = 1,
                        capacity = 12,
                        imageUri = "",
                        isBookmarked = false
                    ), Event(
                        id = 921,
                        name = "야구 모임",
                        content = "야구 모임입니다.",
                        category = "운동",
                        applicants = 1,
                        capacity = 12,
                        imageUri = "",
                        isBookmarked = false
                    )
                )
                _uiState.update { it.copy(isLoading = false, eventList = eventList) }
            } catch (e: HttpException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }
}
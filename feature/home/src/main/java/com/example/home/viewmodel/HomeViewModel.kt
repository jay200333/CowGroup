package com.example.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.EventRepository
import com.example.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

data class HomeUIState(
    val isLoading: Boolean = false,
    val eventList: List<Event> = emptyList(),
    val error: String = "",
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val eventRepository: EventRepository,
) : ViewModel() {
    private val _homeUIState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState())
    val homeUIState: StateFlow<HomeUIState> = _homeUIState.asStateFlow()

    fun getEvents() {
        viewModelScope.launch {
            _homeUIState.value = _homeUIState.value.copy(isLoading = true, error = "")
            try {
                eventRepository.getEvents().collect { eventList ->
                    _homeUIState.value = _homeUIState.value.copy(
                        isLoading = false,
                        eventList = eventList,
                        error = if (eventList.isEmpty()) "데이터가 없습니다." else "",
                    )
                }
            } catch (e: IOException) {
                _homeUIState.value = _homeUIState.value.copy(
                    isLoading = false,
                    error = "이벤트 호출에 실패했습니다.",
                )
            } catch (e: Exception) {
                _homeUIState.value = _homeUIState.value.copy(
                    isLoading = false,
                    error = "알 수 없는 오류가 발생했습니다.",
                )
            }
        }
    }

    fun updateBookmark(eventId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            _homeUIState.value = _homeUIState.value.copy(isLoading = true, error = "")
            try {
                val result = eventRepository.updateBookmark(eventId, isBookmarked)
                if (result) {
                    val updatedEventList = _homeUIState.value.eventList.map { event ->
                        if (event.id == eventId) {
                            event.copy(isBookmarked = isBookmarked)
                        } else {
                            event
                        }
                    }
                    _homeUIState.value = _homeUIState.value.copy(
                        isLoading = false,
                        eventList = updatedEventList,
                    )
                }
            } catch (e: IOException) {
                _homeUIState.value = _homeUIState.value.copy(
                    isLoading = false,
                    error = "이벤트 호출에 실패했습니다.",
                )
            } catch (e: Exception) {
                _homeUIState.value = _homeUIState.value.copy(
                    isLoading = false,
                    error = "알 수 없는 오류가 발생했습니다.",
                )
            }
        }
    }
}

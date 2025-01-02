package com.example.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.data.repository.EventRepository
import com.example.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    fun initViewModel() {
        getEvents()
    }

    private fun getEvents() {
        viewModelScope.launch {
            eventRepository.getEvents().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _homeUIState.value = _homeUIState.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _homeUIState.value = _homeUIState.value.copy(
                            isLoading = false,
                            eventList = result.data ?: emptyList(),
                        )
                    }

                    is Resource.Error -> {
                        _homeUIState.value = _homeUIState.value.copy(
                            isLoading = false,
                            error = result.message ?: "이벤트 호출에 실패했습니다.",
                        )
                    }
                }
            }
        }
    }

    fun updateBookmark(eventId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            _homeUIState.value = _homeUIState.value.copy(isLoading = true)
            val result = eventRepository.updateBookmark(eventId, isBookmarked)
            when (result) {
                is Resource.Success -> {
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

                is Resource.Error -> {
                    _homeUIState.value = _homeUIState.value.copy(
                        isLoading = false,
                        error = result.message ?: "북마크 갱신에 실패했습니다.",
                    )
                }

                else -> {
                    _homeUIState.value = _homeUIState.value.copy(isLoading = false)
                }
            }
        }
    }
}

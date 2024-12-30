package com.example.home.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class HomeUIState(
    val eventList: List<Event> = emptyList(),
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _homeUIState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState())
    val homeUIState: StateFlow<HomeUIState> = _homeUIState.asStateFlow()
    private val dummyEventList: List<Event> = listOf(
        Event(1, "test1", "test1", "2025-12-7", "2024-12-31", 10, 100, false),
        Event(2, "test2", "test2", "2025-11-3", "2024-12-30", 20, 200, true),
        Event(3, "test3", "test3", "2025-12-2", "2024-12-29", 30, 300, true),
    )

    fun initViewModel() {
        getEvents()
    }

    private fun getEvents() {
        _homeUIState.value = _homeUIState.value.copy(eventList = dummyEventList)
    }

    fun updateBookmark(eventId: Int, isBookmarked: Boolean) {
        val updatedEventList = _homeUIState.value.eventList.map { event ->
            if (event.id == eventId) {
                event.copy(isBookmarked = isBookmarked)
            } else {
                event
            }
        }
    }
}

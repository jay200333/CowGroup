package com.example.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.model.DetailEvent
import com.example.navigation.EventDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class EventDetailUIState(
    val isLoading: Boolean = false,
    val isLogout: Boolean = false,
    val detailEvent: DetailEvent = DetailEvent(
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
class EventDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle): ViewModel() {
    private val eventId: Int = savedStateHandle.toRoute<EventDetailRoute>().eventId
    private val _eventDetailUIState: MutableStateFlow<EventDetailUIState> = MutableStateFlow(EventDetailUIState())
    val eventDetailUIState: StateFlow<EventDetailUIState> = _eventDetailUIState.asStateFlow()
    init {
        getEventDetail()
    }

    private fun getEventDetail() {}
}
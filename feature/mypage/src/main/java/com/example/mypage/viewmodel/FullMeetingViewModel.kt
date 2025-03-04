package com.example.mypage.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.repository.EventRepository
import com.example.model.Event
import com.example.navigation.FullMeetingRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class FullMeetingUIState(
    val isLoading: Boolean = false,
    val isBookmarkPage: Boolean = false,
    val eventList: PagingData<Event> = PagingData.empty(),
    val message: String = "",
)

@HiltViewModel
class FullMeetingViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _fullMeetingUIState: MutableStateFlow<FullMeetingUIState> =
        MutableStateFlow(FullMeetingUIState())
    val fullMeetingUIState: StateFlow<FullMeetingUIState> = _fullMeetingUIState.asStateFlow()
    private val isBookmarkPage: Boolean = savedStateHandle.toRoute<FullMeetingRoute>().isBookmarkPage
    private val pagingEvents: Flow<PagingData<Event>> = flow {
        val flow = if (isBookmarkPage) {
            eventRepository.getPagingHomeEvents(10)
        } else {
            eventRepository.getPagingParticipateEvents(10)
        }
        emitAll(flow)
    }.cachedIn(viewModelScope)

    init {
        initializePagingMode()
        initializePagingEvents()
    }

    private fun initializePagingMode() {
        _fullMeetingUIState.update { state -> state.copy(isBookmarkPage = isBookmarkPage) }
    }

    private fun initializePagingEvents() {
        pagingEvents.onEach { pagingEvents ->
            _fullMeetingUIState.update {
                it.copy(
                    isLoading = false,
                    eventList = pagingEvents
                )
            }
        }.launchIn(viewModelScope)
    }

    fun setMessageClear() {
        _fullMeetingUIState.update { state ->
            state.copy(message = "")
        }
    }
}
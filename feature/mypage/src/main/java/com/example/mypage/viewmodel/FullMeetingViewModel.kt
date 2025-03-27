package com.example.mypage.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
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
import kotlinx.coroutines.launch
import retrofit2.HttpException
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
            eventRepository.getPagingBookmarkEvents(10)
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

    fun updateBookmark(eventId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            _fullMeetingUIState.update { it.copy(isLoading = true, message = "") }
            try {
                eventRepository.updateBookmark(eventId, isBookmarked)
                _fullMeetingUIState.update {
                    it.copy(
                        isLoading = false,
                        message = "북마크가 업데이트 되었습니다.",
                        eventList = it.eventList.map { event ->
                            if (event.id == eventId) {
                                event.copy(isBookmarked = isBookmarked.not())
                            } else {
                                event
                            }
                        })
                }
            } catch (e: HttpException) {
                _fullMeetingUIState.update {
                    it.copy(
                        isLoading = false,
                        message = "북마크 업데이트에 실패했습니다.",
                    )
                }
            } catch (e: Exception) {
                _fullMeetingUIState.update {
                    it.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun setMessageClear() {
        _fullMeetingUIState.update { state ->
            state.copy(message = "")
        }
    }
}
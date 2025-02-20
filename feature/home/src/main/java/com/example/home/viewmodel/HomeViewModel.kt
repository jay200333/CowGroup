package com.example.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.repository.EventRepository
import com.example.datastore.CowGroupDataStore
import com.example.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

data class HomeUIState(
    val isLoading: Boolean = false,
    val isLogout: Boolean = false,
    val eventList: List<Event> = emptyList(),
    val message: String = "",
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val dataStore: CowGroupDataStore,
) : ViewModel() {
    private val _homeUIState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState())
    val homeUIState: StateFlow<HomeUIState> = _homeUIState.asStateFlow()

    val pagingEvents: Flow<PagingData<Event>> =
        eventRepository.getPagingEvents(10).cachedIn(viewModelScope)

//    fun getEvents() {
//        viewModelScope.launch {
//            _homeUIState.update { it.copy(isLoading = true, message = "") }
//            try {
//                eventRepository.getEvents().collect { eventList ->
//                    _homeUIState.update {
//                        it.copy(
//                            isLoading = false,
//                            eventList = eventList,
//                            message = if (eventList.isEmpty()) "데이터가 없습니다." else "",
//                        )
//                    }
//                }
//            } catch (e: IOException) {
//                _homeUIState.update {
//                    it.copy(
//                        isLoading = false,
//                        message = "이벤트 호출에 실패했습니다.",
//                    )
//                }
//            } catch (e: Exception) {
//                _homeUIState.update {
//                    it.copy(
//                        isLoading = false,
//                        message = "알 수 없는 오류가 발생했습니다.",
//                    )
//                }
//            }
//        }
//    }

    fun updateBookmark(eventId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            _homeUIState.value = _homeUIState.value.copy(isLoading = true, message = "")
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
                    message = "이벤트 호출에 실패했습니다.",
                )
            } catch (e: Exception) {
                _homeUIState.value = _homeUIState.value.copy(
                    isLoading = false,
                    message = "알 수 없는 오류가 발생했습니다.",
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStore.clearToken()
            _homeUIState.update { state ->
                state.copy(isLogout = true)
            }
        }
    }

    fun setMessageClear() {
        _homeUIState.update { state ->
            state.copy(message = "")
        }
    }
}

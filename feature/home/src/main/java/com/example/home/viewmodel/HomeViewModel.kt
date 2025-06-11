package com.example.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.data.model.SearchHistory
import com.example.data.repository.EventRepository
import com.example.data.repository.SearchHistoryRepository
import com.example.datastore.CowGroupDataStore
import com.example.model.Category
import com.example.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class HomeUIState(
    val isLoading: Boolean = false,
    val isLogout: Boolean = false,
    val eventList: PagingData<Event> = PagingData.empty(),
    val selectedCategory: Category? = null,
    val message: String = "",
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val searchHistoryRepository: SearchHistoryRepository,
    private val dataStore: CowGroupDataStore,
) : ViewModel() {
    private val _homeUIState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState())
    val homeUIState: StateFlow<HomeUIState> = _homeUIState.asStateFlow()

    private val pagingEvents: Flow<PagingData<Event>> =
        eventRepository.getPagingHomeEvents(10).cachedIn(viewModelScope)

    init {
        pagingEvents.onEach { pagingEvents ->
            _homeUIState.update {
                it.copy(
                    isLoading = false,
                    eventList = pagingEvents
                )
            }
        }.launchIn(viewModelScope)
    }

    fun updateCategory(category: Category?) {
        _homeUIState.update { it.copy(selectedCategory = category) }
    }

    fun updateBookmark(eventId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            _homeUIState.update { it.copy(isLoading = true, message = "") }
            try {
                eventRepository.updateBookmark(eventId, isBookmarked)
                _homeUIState.update {
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
                _homeUIState.update {
                    it.copy(
                        isLoading = false,
                        message = "북마크 업데이트에 실패했습니다.",
                    )
                }
            } catch (e: Exception) {
                _homeUIState.update {
                    it.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
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

    fun insertQuery(query: String) {
        if (query.isNotBlank()) {
            viewModelScope.launch {
                searchHistoryRepository.insertSearchHistory(query)
            }
        }
    }

    fun deleteSearchHistory(query: String) {
        viewModelScope.launch {
            searchHistoryRepository.deleteSearchHistory(query)
        }
    }

    fun getRecentSearches(): Flow<List<SearchHistory>> {
        return searchHistoryRepository.getRecentSearches()
    }
}

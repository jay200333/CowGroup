package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.model.SearchHistory
import com.example.data.repository.RegularMeetingRepository
import com.example.data.repository.RegularMeetingSearchHistoryRepository
import com.example.model.SearchRegularEvent
import com.example.model.SearchRegularMeetingRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class HomeCalendarUISTate(
    val isLoading: Boolean = false,
    val startDate: Long? = LocalDate.now()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli(),
    val endDate: Long? = LocalDate.now().plusDays(6)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli(),
    val dateList: List<LocalDate> = emptyList(),
    val regularEventList: PagingData<SearchRegularEvent> = PagingData.empty(),
    val selectedDate: LocalDate = LocalDate.now(),
    val searchTerm: String = "",
    val message: String = ""
)

@HiltViewModel
class HomeCalendarViewModel @Inject constructor(
    private val regularMeetingSearchHistoryRepository: RegularMeetingSearchHistoryRepository,
    private val regularMeetingRepository: RegularMeetingRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeCalendarUISTate> =
        MutableStateFlow((HomeCalendarUISTate()))
    val uiState: StateFlow<HomeCalendarUISTate> = _uiState.asStateFlow()

    private val _searchRequestFlow = MutableStateFlow(SearchRegularMeetingRequest("", LocalDate.now().format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
    val pagingEvents: Flow<PagingData<SearchRegularEvent>> =
        _searchRequestFlow.flatMapLatest { searchRequest ->
            regularMeetingRepository.getPagedRegularEventsBySearch(
                10,
                searchRequest
            )
        }.cachedIn(viewModelScope)

    init {
        pagingEvents.onEach { pagingEvents ->
            _uiState.update { it.copy(isLoading = false, regularEventList = pagingEvents) }
        }.launchIn(viewModelScope)
        updateDateList()
    }

    fun updateDateRange(selectedDateRange: Pair<Long?, Long?>) {
        _uiState.update {
            it.copy(
                startDate = selectedDateRange.first,
                endDate = selectedDateRange.second
            )
        }
        updateDateList()
        Log.d("calendar", "시작일 : ${_uiState.value.startDate} 끝일 : ${_uiState.value.endDate}")
        Log.d("calendar", "${_uiState.value.dateList}")
    }

    private fun updateDateList() {
        val startDate =
            Instant.ofEpochMilli(_uiState.value.startDate!!).atZone(ZoneId.systemDefault())
                .toLocalDate()
        val endDate = Instant.ofEpochMilli(_uiState.value.endDate!!).atZone(ZoneId.systemDefault())
            .toLocalDate()

        val dateList = generateSequence(startDate) { it.plusDays(1) }
            .takeWhile { !it.isAfter(endDate) }
            .toList()
        _uiState.update { it.copy(dateList = dateList) }
    }

    fun updateSelectedDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
    }

    fun updateSearchTerm(searchTerm: String) {
        _uiState.update { it.copy(searchTerm = searchTerm) }
    }

    fun insertQuery(query: String) {
        if (query.isNotBlank()) {
            viewModelScope.launch {
                regularMeetingSearchHistoryRepository.insertSearchHistory(query)
            }
        }
    }

    fun deleteSearchHistory(query: String) {
        viewModelScope.launch {
            regularMeetingSearchHistoryRepository.deleteSearchHistory(query)
        }
    }

    fun getRecentSearches(): Flow<List<SearchHistory>> {
        return regularMeetingSearchHistoryRepository.getRecentSearches()
    }
}
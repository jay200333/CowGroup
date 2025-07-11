package com.example.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.SearchRegularEvent
import com.example.model.SearchRegularMeetingRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class MeetingScheduleUIState(
    val isLoading: Boolean = false,
    val startDate: Long? = LocalDate.now()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli(),
    val endDate: Long? = LocalDate.now().plusDays(6)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli(),
    val eventList: List<SearchRegularEvent> = emptyList(),
    val dateList: List<LocalDate> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
)

@HiltViewModel
class MeetingScheduleViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<MeetingScheduleUIState> =
        MutableStateFlow((MeetingScheduleUIState()))
    val uiState: StateFlow<MeetingScheduleUIState> = _uiState.asStateFlow()
    private val _searchRequestFlow = MutableStateFlow(
        SearchRegularMeetingRequest(
            "", LocalDate.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            )
        )
    )

    init {
        updateDateList()
        getEventList()
    }

    private fun getEventList() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val regularEventList = listOf(
                SearchRegularEvent(
                    id = 29,
                    regularId = 74,
                    name = "android study",
                    category = "자기계발",
                    accessUrl = "",
                    dateTime = "2025-06-28T05:23:00",
                    capacity = 10,
                    applicants = 8,
                    eventParticipated = true
                ), SearchRegularEvent(
                    id = 30,
                    regularId = 73,
                    name = "풋살",
                    category = "스포츠",
                    accessUrl = "",
                    dateTime = "2025-07-30T06:03:00",
                    capacity = 30,
                    applicants = 24,
                    eventParticipated = true
                )
            )
            _uiState.update { it.copy(isLoading = false, eventList = regularEventList) }
        }
    }

    fun updateDateRange(selectedDateRange: Pair<Long?, Long?>) {
        _uiState.update {
            it.copy(
                startDate = selectedDateRange.first,
                endDate = selectedDateRange.second
            )
        }
        updateDateList()
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
        updateSelectedDate(dateList.first())
    }

    fun updateSelectedDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        _searchRequestFlow.update { it.copy(date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) }
    }
}
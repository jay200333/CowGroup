package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
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
    val selectedDate: LocalDate = LocalDate.now()
)

@HiltViewModel
class HomeCalendarViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeCalendarUISTate> =
        MutableStateFlow((HomeCalendarUISTate()))
    val uiState: StateFlow<HomeCalendarUISTate> = _uiState.asStateFlow()
    init {
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
        val startDate = Instant.ofEpochMilli(_uiState.value.startDate!!).atZone(ZoneId.systemDefault()).toLocalDate()
        val endDate = Instant.ofEpochMilli(_uiState.value.endDate!!).atZone(ZoneId.systemDefault()).toLocalDate()

        val dateList = generateSequence(startDate) { it.plusDays(1) }
            .takeWhile { !it.isAfter(endDate) }
            .toList()
        _uiState.update { it.copy(dateList = dateList) }
    }

    fun updateSelectedDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
    }
}
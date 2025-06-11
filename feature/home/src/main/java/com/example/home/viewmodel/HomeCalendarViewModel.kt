package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.RegularEvent
import com.example.network.model.ErrorResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
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
    val selectedDate: LocalDate = LocalDate.now(),
    val regularEventList: List<RegularEvent> = emptyList(), // pagingData로 교체해야함.
    val message: String = ""
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

    fun getRegularEventList() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val regularEventList = listOf(
                    RegularEvent(
                        id = 0,
                        participationId = 0,
                        name = "비 맞으며 뛰는게 국룰이지",
                        location = "여의도",
                        dateTime = "4월 30일 오후 7:30",
                        capacity = 20,
                        applicants = 10,
                        isRegularRegistrant = false
                    ),
                    RegularEvent(
                        id = 1,
                        participationId = 0,
                        name = "여의도 한강공원 풋살하실분",
                        location = "여의도",
                        dateTime = "5월 10일 오후 2:30",
                        capacity = 40,
                        applicants = 30,
                        isRegularRegistrant = false
                    ),
                    RegularEvent(
                        id = 2,
                        participationId = 0,
                        name = "아이콘 매치 시즌 2 가실분",
                        location = "여의도",
                        dateTime = "7월 28일 오후 10:30",
                        capacity = 70,
                        applicants = 57,
                        isRegularRegistrant = false
                    )
                )
                _uiState.update { it.copy(isLoading = false, regularEventList = regularEventList) }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다."
                    )
                }
            }
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
}
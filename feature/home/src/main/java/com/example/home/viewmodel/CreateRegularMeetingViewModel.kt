package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.DateUtil
import com.example.data.repository.RegularMeetingRepository
import com.example.model.CreateRegularMeeting
import com.example.network.model.ErrorResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class CreateRegularMeetingUIState(
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val regularMeeting: CreateRegularMeeting = CreateRegularMeeting(
        name = "",
        dateTime = "",
        location = "",
        capacity = 0
    ),
    val createButtonEnabled: Boolean = false,
    val isCreateRegularMeetingSuccess: Boolean = false,
    val regularMeetingDate: String = "",
    val regularMeetingTime: String = "",
    val dateTime: String = "",
    val eventId: Int = 0,
    val isValidCapacity: Boolean = false,
    val capacityMessage: String = "",
    val message: String = ""
)

@HiltViewModel
class CreateRegularMeetingViewModel @Inject constructor(
    private val regularMeetingRepository: RegularMeetingRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _createRegularMeetingUIState: MutableStateFlow<CreateRegularMeetingUIState> =
        MutableStateFlow(CreateRegularMeetingUIState())
    val createRegularMeetingUIState: StateFlow<CreateRegularMeetingUIState> =
        _createRegularMeetingUIState.asStateFlow()
    private val eventId: Int =
        requireNotNull(savedStateHandle.get<Int>("eventId")) { "eventId is required." }
    private val isEditMode: Boolean =
        requireNotNull(savedStateHandle.get<Boolean>("isEditMode")) { "isEditMode is required." }

    init {
        Log.d("CreateRegularViewModel", "${eventId}, $isEditMode")
    }

    fun createRegularMeeting() {
        viewModelScope.launch {
            _createRegularMeetingUIState.update { state ->
                state.copy(
                    isLoading = true,
                    message = ""
                )
            }
            try {
                regularMeetingRepository.createRegularMeeting(
                    903,//eventId
                    createRegularMeetingUIState.value.regularMeeting
                )
                _createRegularMeetingUIState.update { state ->
                    state.copy(
                        isCreateRegularMeetingSuccess = true,
                        isLoading = false,
                        message = "정기 모임 생성이 완료되었습니다."
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _createRegularMeetingUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message
                    )
                }
            } catch (e: Exception) {
                _createRegularMeetingUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다."
                    )
                }
            }
        }
    }

    fun setRegularMeetingDate(date: String) {
        _createRegularMeetingUIState.update { state ->
            state.copy(regularMeetingDate = date)
        }
        setDateTime(
            _createRegularMeetingUIState.value.regularMeetingDate,
            _createRegularMeetingUIState.value.regularMeetingTime
        )
    }

    fun setRegularMeetingTime(time: String) {
        _createRegularMeetingUIState.update { state ->
            state.copy(regularMeetingTime = time)
        }
        setDateTime(
            _createRegularMeetingUIState.value.regularMeetingDate,
            _createRegularMeetingUIState.value.regularMeetingTime
        )
    }

    private fun setDateTime(date: String, time: String) {
        if (date.isNotEmpty() && time.isNotEmpty()) {
            val dateTime = DateUtil.formatDateTimeToIso8601(
                _createRegularMeetingUIState.value.regularMeetingDate,
                _createRegularMeetingUIState.value.regularMeetingTime
            )
            _createRegularMeetingUIState.update { state ->
                val regularMeeting = state.regularMeeting.copy(dateTime = dateTime)
                state.copy(regularMeeting = regularMeeting)
            }
        }
    }

    fun updateName(name: String) {
        _createRegularMeetingUIState.update { state ->
            val updatedRegularMeeting = state.regularMeeting.copy(name = name)
            state.copy(
                regularMeeting = updatedRegularMeeting,
                createButtonEnabled = createRegularMeetingCondition(state.copy(regularMeeting = updatedRegularMeeting)),
            )
        }
    }

    fun updateLocation(location: String) {
        _createRegularMeetingUIState.update { state ->
            val updatedRegularMeeting = state.regularMeeting.copy(location = location)
            state.copy(
                regularMeeting = updatedRegularMeeting,
                createButtonEnabled = createRegularMeetingCondition(state.copy(regularMeeting = updatedRegularMeeting))
            )
        }
    }

    fun updateCapacity(capacity: String) {
        _createRegularMeetingUIState.update { state ->
            val updatedRegularMeeting = if (capacity.isEmpty()) {
                state.regularMeeting.copy(capacity = 0)
            } else state.regularMeeting.copy(capacity = capacity.toInt())
            val isCapacityValid = (capacity.toIntOrNull() ?: 0) in 1..100
            val capacityMessage = if (isCapacityValid) "" else "모임원 수는 최대 100명입니다."

            state.copy(
                regularMeeting = updatedRegularMeeting,
                createButtonEnabled = createRegularMeetingCondition(
                    state.copy(
                        regularMeeting = updatedRegularMeeting,
                        capacityMessage = capacityMessage,
                        isValidCapacity = isCapacityValid
                    )
                ),
                capacityMessage = capacityMessage,
                isValidCapacity = isCapacityValid
            )
        }
    }

    private fun createRegularMeetingCondition(
        state: CreateRegularMeetingUIState
    ): Boolean =
        state.regularMeeting.name.isNotEmpty() &&
                state.regularMeeting.dateTime.isNotEmpty() &&
                state.regularMeeting.location.isNotEmpty() &&
                (state.regularMeeting.capacity > 0)

    fun setMessageClear() {
        _createRegularMeetingUIState.update { state ->
            state.copy(message = "")
        }
    }
}
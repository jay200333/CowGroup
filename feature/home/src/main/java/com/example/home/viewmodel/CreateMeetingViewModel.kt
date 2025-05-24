package com.example.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ImageProcessor
import com.example.data.repository.EventRepository
import com.example.model.Category
import com.example.model.CreateMeeting
import com.example.network.model.ErrorResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

data class CreateMeetingUIState(
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val createButtonEnabled: Boolean = false,
    val isCreateMeetingSuccess: Boolean = false,
    val isEditMeetingSuccess: Boolean = false,
    val createMeeting: CreateMeeting = CreateMeeting(
        name = "",
        category = Category.SPORTS,
        capacity = 0,
        content = "",
        file = null
    ),
    val eventId: Int = 0,
    val isValidCapacity: Boolean = false,
    val capacityMessage: String = "",
    val message: String = "",
)

@HiltViewModel
class CreateMeetingViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    val imageProcessor: ImageProcessor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _createMeetingUIState: MutableStateFlow<CreateMeetingUIState> =
        MutableStateFlow(CreateMeetingUIState())
    val createMeetingUIState: StateFlow<CreateMeetingUIState> = _createMeetingUIState.asStateFlow()
    private val eventId: Int =
        requireNotNull(savedStateHandle.get<Int>("eventId")) { "eventId is required." }
    private val isEditMode: Boolean =
        requireNotNull(savedStateHandle.get<Boolean>("isEditMode")) { "isEditMode is required." }

    init {
        checkEditMode()
    }

    private fun checkEditMode() {
        _createMeetingUIState.update { state ->
            state.copy(isEditMode = isEditMode)
        }
    }

    fun createMeeting() {
        viewModelScope.launch {
            _createMeetingUIState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                val result = eventRepository.createMeeting(createMeetingUIState.value.createMeeting)
                _createMeetingUIState.update { state ->
                    state.copy(
                        isCreateMeetingSuccess = true,
                        isLoading = false,
                        eventId = result,
                        message = "모임 생성이 완료되었습니다.",
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _createMeetingUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "모임 등록이 실패하였습니다."//errorResponse.errors.message //"모임 등록이 실패하였습니다."
                    )
                }
            } catch (e: Exception) {
                _createMeetingUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun editMeeting() {
        viewModelScope.launch {
            _createMeetingUIState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                eventRepository.editEvent(eventId, createMeetingUIState.value.createMeeting)
                _createMeetingUIState.update { state ->
                    state.copy(
                        isCreateMeetingSuccess = true,
                        isLoading = false,
                        message = "모임 수정이 완료되었습니다.",
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _createMeetingUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message //"모임 수정이 실패하였습니다."
                    )
                }
            } catch (e: Exception) {
                _createMeetingUIState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun updateFile(file: File?) {
        _createMeetingUIState.update { state ->
            val createMeeting = state.createMeeting.copy(file = file)
            state.copy(createMeeting = createMeeting)
        }
    }

    fun updateName(name: String) {
        _createMeetingUIState.update { state ->
            val updatedCreateMeeting = state.createMeeting.copy(name = name)
            state.copy(
                createMeeting = updatedCreateMeeting,
                createButtonEnabled = createMeetingCondition(state.copy(createMeeting = updatedCreateMeeting)),
            )
        }
    }

    fun updateCategory(label: String) {
        _createMeetingUIState.update { state ->
            val updatedCreateMeeting =
                state.createMeeting.copy(category = Category.fromLabel(label) ?: Category.SPORTS)
            state.copy(
                createMeeting = updatedCreateMeeting,
                createButtonEnabled = createMeetingCondition(state.copy(createMeeting = updatedCreateMeeting)),
            )
        }
    }

    fun updateCapacity(capacity: String) {
        _createMeetingUIState.update { state ->
            val updatedCreateMeeting = if (capacity.isEmpty()) {
                state.createMeeting.copy(capacity = 0)
            } else state.createMeeting.copy(capacity = capacity.toInt())
            val isCapacityValid = (capacity.toIntOrNull() ?: 0) in 1..100
            val capacityMessage = if (isCapacityValid) "" else "모임원 수는 최대 100명입니다."

            state.copy(
                createMeeting = updatedCreateMeeting,
                createButtonEnabled = createMeetingCondition(
                    state.copy(
                        createMeeting = updatedCreateMeeting,
                        capacityMessage = capacityMessage,
                        isValidCapacity = isCapacityValid
                    )
                ),
                capacityMessage = capacityMessage,
                isValidCapacity = isCapacityValid
            )
        }
    }

    fun updateContent(content: String) {
        _createMeetingUIState.update { state ->
            val updatedCreateMeeting = state.createMeeting.copy(content = content)
            state.copy(
                createMeeting = updatedCreateMeeting,
                createButtonEnabled = createMeetingCondition(state.copy(createMeeting = updatedCreateMeeting)),
            )
        }
    }

    fun setMessageClear() {
        _createMeetingUIState.update { state ->
            state.copy(message = "")
        }
    }

    private fun createMeetingCondition(
        state: CreateMeetingUIState,
    ): Boolean = state.createMeeting.name.isNotEmpty() &&
            (state.createMeeting.capacity > 0) &&
            state.createMeeting.content.isNotEmpty() &&
            state.isValidCapacity
}

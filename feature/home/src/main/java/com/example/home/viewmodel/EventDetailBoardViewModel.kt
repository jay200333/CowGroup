package com.example.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Event
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

data class EventDetailBoardUIState(
    val isLoading: Boolean = false,
    val post: List<Event> = emptyList(),
    val message: String = ""
)

@HiltViewModel
class EventDetailBoardViewModel @Inject constructor(): ViewModel() {
    private val _uiState: MutableStateFlow<EventDetailBoardUIState> = MutableStateFlow(EventDetailBoardUIState())
    val uiState: StateFlow<EventDetailBoardUIState> = _uiState.asStateFlow()

    fun getPosts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                true
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

    fun setMessageClear() {
        _uiState.update { state ->
            state.copy(message = "")
        }
    }
}
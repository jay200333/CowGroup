package com.example.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

data class BookmarkMeetingUIState(
    val isLoading: Boolean = false,
    val message: String = "",
    val bookmarkEventList: List<Event> = emptyList()
)

@HiltViewModel
class BookmarkMeetingViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<BookmarkMeetingUIState> =
        MutableStateFlow(BookmarkMeetingUIState())
    val uiState: StateFlow<BookmarkMeetingUIState> = _uiState.asStateFlow()

    init {
        getEventList()
    }

    private fun getEventList() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val bookmarkEventList = listOf(
                    Event(
                        id = 922,
                        name = "풋살 모임",
                        content = "풋살 모임입니다.",
                        category = "운동",
                        applicants = 1,
                        capacity = 12,
                        imageUri = "",
                        isBookmarked = false
                    ), Event(
                        id = 921,
                        name = "야구 모임",
                        content = "야구 모임입니다.",
                        category = "운동",
                        applicants = 1,
                        capacity = 12,
                        imageUri = "",
                        isBookmarked = false
                    )
                )
                _uiState.update { it.copy(isLoading = false, bookmarkEventList = bookmarkEventList) }
            } catch (e: HttpException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun updateBookmark(eventId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            try {
                //eventRepository.updateBookmark(eventId, isBookmarked)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = "북마크가 업데이트 되었습니다.",
                        bookmarkEventList = it.bookmarkEventList.map { event ->
                            if (event.id == eventId) {
                                event.copy(isBookmarked = isBookmarked.not())
                            } else {
                                event
                            }
                        })
                }
            } catch (e: HttpException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = "북마크 업데이트에 실패했습니다.",
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }
}
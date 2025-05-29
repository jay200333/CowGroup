package com.example.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.repository.PostRepository
import com.example.model.Post
import com.example.navigation.EventDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class EventDetailBoardUIState(
    val isLoading: Boolean = false,
    val postList: PagingData<Post> = PagingData.empty(),
    val message: String = ""
)

@HiltViewModel
class EventDetailBoardViewModel @Inject constructor(
    postRepository: PostRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val eventId: Int = savedStateHandle.toRoute<EventDetailRoute>().eventId
    private val _uiState: MutableStateFlow<EventDetailBoardUIState> =
        MutableStateFlow(EventDetailBoardUIState())
    val uiState: StateFlow<EventDetailBoardUIState> = _uiState.asStateFlow()
    private val pagingPost: Flow<PagingData<Post>> =
        postRepository.getPagingPosts(pageSize = 10, eventId = eventId).cachedIn(viewModelScope)

    init {
        pagingPost.onEach { pagingPost ->
            _uiState.update {
                it.copy(
                    isLoading = false,
                    postList = pagingPost
                )
            }
        }.launchIn(viewModelScope)
    }

    fun setMessageClear() {
        _uiState.update { state ->
            state.copy(message = "")
        }
    }
}
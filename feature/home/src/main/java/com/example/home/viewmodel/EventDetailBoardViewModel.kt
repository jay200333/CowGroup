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
import com.example.network.model.ErrorResponse
import com.google.gson.Gson
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

data class EventDetailBoardUIState(
    val isLoading: Boolean = false,
    val postList: PagingData<Post> = PagingData.empty(),
    val message: String = ""
)

@HiltViewModel
class EventDetailBoardViewModel @Inject constructor(
    private val postRepository: PostRepository,
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

    fun deletePost(postId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "") }
            try {
                postRepository.deletePost(postId)
                _uiState.update { it.copy(isLoading = false, message = "게시글이 삭제되었습니다.") }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = errorResponse.errors.message
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, message = "알 수 없는 오류가 발생했습니다.")
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
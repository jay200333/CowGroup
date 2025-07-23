package com.example.home.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.repository.CommentRepository
import com.example.data.repository.PostRepository
import com.example.model.Comment
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

data class EventDetailCommentUIState(
    val isLoading: Boolean = false,
    val isEditMode: Boolean = false,
    val sendButtonEnabled: Boolean = false,
    val comment: TextFieldValue = TextFieldValue(),
    val commentList: List<Comment> = emptyList(),
    val message: String = ""
)

@HiltViewModel
class EventDetailBoardViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val eventId: Int = savedStateHandle.toRoute<EventDetailRoute>().eventId
    private val _uiState: MutableStateFlow<EventDetailBoardUIState> =
        MutableStateFlow(EventDetailBoardUIState())
    val uiState: StateFlow<EventDetailBoardUIState> = _uiState.asStateFlow()
    private val pagingPost: Flow<PagingData<Post>> =
        postRepository.getPagingPosts(pageSize = 10, eventId = eventId).cachedIn(viewModelScope)

    private val _commentUiState: MutableStateFlow<EventDetailCommentUIState> =
        MutableStateFlow(EventDetailCommentUIState())
    val commentUiState: StateFlow<EventDetailCommentUIState> = _commentUiState.asStateFlow()

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

    fun getCommentList(postId: Int) {
        viewModelScope.launch {
            _commentUiState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                val result = commentRepository.getCommentList(postId)
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        commentList = result
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.message
                    )
                }
            } catch (e: Exception) {
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun postComment(postId: Int) {
        viewModelScope.launch {
            _commentUiState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                commentRepository.createComment(postId, _commentUiState.value.comment.text)
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        sendButtonEnabled = false,
                        comment = TextFieldValue(text = ""),
                        message = "댓글이 등록되었습니다."
                    )
                }
                getCommentList(postId)
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message
                    )
                }
            } catch (e: Exception) {
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun deleteComment(postId: Int?, commentId: Int) {
        viewModelScope.launch {
            _commentUiState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                commentRepository.deleteComment(commentId)
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "댓글이 삭제되었습니다."
                    )
                }
                getCommentList(postId!!)
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message
                    )
                }
            } catch (e: Exception) {
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun editComment(commentId: Int) {
        viewModelScope.launch {
            _commentUiState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                commentRepository.editComment(commentId, _commentUiState.value.comment.text)
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        isEditMode = false,
                        sendButtonEnabled = false,
                        comment = TextFieldValue(text = ""),
                        message = "댓글이 수정되었습니다."
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message
                    )
                }
            } catch (e: Exception) {
                _commentUiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun switchEditMode(commentId: Int) {
        val editComment = _commentUiState.value.commentList.find { it.id == commentId }
        val content = editComment?.content.orEmpty()
        _commentUiState.update { state ->
            state.copy(
                isEditMode = true,
                comment = TextFieldValue(
                    text = content,
                    selection = TextRange(content.length)
                )
            )
        }
    }

    fun cancelEditMode() {
        _commentUiState.update { state ->
            state.copy(
                isEditMode = false,
                comment = TextFieldValue(text = "")
            )
        }
    }

    fun updateComment(comment: TextFieldValue) {
        _commentUiState.update { state ->
            state.copy(
                comment = comment,
                sendButtonEnabled = comment.text.isNotBlank()
            )
        }
    }

    fun setMessageClear() {
        _uiState.update { state ->
            state.copy(message = "")
        }
    }
}
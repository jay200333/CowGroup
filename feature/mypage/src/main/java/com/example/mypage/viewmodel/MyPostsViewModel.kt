package com.example.mypage.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.CommentRepository
import com.example.model.Comment
import com.example.model.Post
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

data class MyPostsUIState(
    val isLoading: Boolean = false,
    val message: String = "",
    val postList: List<Post> = emptyList()
)

data class MyPostsCommentUIState(
    val isLoading: Boolean = false,
    val isEditMode: Boolean = false,
    val sendButtonEnabled: Boolean = false,
    val comment: TextFieldValue = TextFieldValue(),
    val commentList: List<Comment> = emptyList(),
    val message: String = ""
)

@HiltViewModel
class MyPostsViewModel @Inject constructor(private val commentRepository: CommentRepository) :
    ViewModel() {
    private val _uiState: MutableStateFlow<MyPostsUIState> = MutableStateFlow(MyPostsUIState())
    val uiState: StateFlow<MyPostsUIState> = _uiState.asStateFlow()

    private val _commentUiState: MutableStateFlow<MyPostsCommentUIState> =
        MutableStateFlow(MyPostsCommentUIState())
    val commentUiState: StateFlow<MyPostsCommentUIState> = _commentUiState.asStateFlow()

    init {
        getPostList()
    }

    private fun getPostList() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val samplePostList = listOf(
                Post(
                    id = 19,
                    title = "게시글 테스트",
                    content = "게시글 내용",
                    dateTime = "2025-07-16T17:24:24.837584",
                    userName = "manager",
                    commentCount = 1,
                    isRegistrant = true
                ),
                Post(
                    id = 19,
                    title = "게시글 테스트2",
                    content = "게시글 내용",
                    dateTime = "2025-07-16T17:24:24.837584",
                    userName = "manager",
                    commentCount = 1,
                    isRegistrant = true
                )
            )
            _uiState.update {
                it.copy(
                    isLoading = false,
                    postList = samplePostList
                )
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
}
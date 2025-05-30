package com.example.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.CommentRepository
import com.example.model.Comment
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

data class CommentUIState(
    val isLoading: Boolean = false,
    val isEditMode: Boolean = false,
    val sendButtonEnabled: Boolean = false,
    val commentList: List<Comment> = emptyList(),
    val comment: String = "",
    val message: String = ""
)

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentRepository: CommentRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CommentUIState())
    val uiState: StateFlow<CommentUIState> = _uiState.asStateFlow()

    fun getCommentList(postId: Int) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                val result = commentRepository.getCommentList(postId)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        commentList = result
                    )
                }
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message //"모임 등록이 실패하였습니다."
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
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
            _uiState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                commentRepository.createComment(postId, _uiState.value.comment)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        sendButtonEnabled = false,
                        comment = "",
                        message = "댓글이 등록되었습니다."
                    )
                }
                getCommentList(postId)
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message //"모임 등록이 실패하였습니다."
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
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
            _uiState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                commentRepository.deleteComment(commentId)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "댓글이 삭제되었습니다."
                    )
                }
                getCommentList(postId!!)
            } catch (e: HttpException) {
                val response = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(response, ErrorResponse::class.java)
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = errorResponse.errors.message //"모임 등록이 실패하였습니다."
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        message = "알 수 없는 오류가 발생했습니다.",
                    )
                }
            }
        }
    }

    fun updateComment(comment: String) {
        _uiState.update { state ->
            state.copy(
                comment = comment,
                sendButtonEnabled = comment.isNotEmpty()
            )
        }
    }
}
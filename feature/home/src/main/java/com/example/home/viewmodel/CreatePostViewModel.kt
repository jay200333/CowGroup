package com.example.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.PostRepository
import com.example.model.CreatePost
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

data class CreatePostUIState(
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val post: CreatePost = CreatePost(
        subject = "",
        content = ""
    ),
    val createButtonEnabled: Boolean = false,
    val isCreatePostSuccess: Boolean = false,
    val isEditPostSuccess: Boolean = false,
    val message: String = ""
)

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState: MutableStateFlow<CreatePostUIState> =
        MutableStateFlow(CreatePostUIState())
    val uiState: StateFlow<CreatePostUIState> = _uiState.asStateFlow()
    private val eventId: Int =
        requireNotNull(savedStateHandle.get<Int>("eventId")) { "eventId is required." }
    private val postId: Int =
        requireNotNull(savedStateHandle.get<Int>("postId")) { "postId is required." }
    private val isEditMode: Boolean =
        requireNotNull(savedStateHandle.get<Boolean>("isEditMode")) { "isEditMode is required." }

    init {
        checkEditMode()
        if (isEditMode) {
            getPost(postId)
        }
    }

    private fun checkEditMode() {
        _uiState.update { state ->
            state.copy(isEditMode = isEditMode)
        }
    }

    private fun getPost(postId: Int) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                val result = postRepository.getPost(postId)
                _uiState.update { state ->
                    state.copy(
                        post = result,
                        isLoading = false
                    )
                }
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

    fun createPost() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                postRepository.createPost(eventId, uiState.value.post)
                _uiState.update { state ->
                    state.copy(
                        isCreatePostSuccess = true,
                        isLoading = false,
                        message = "게시글 생성이 완료되었습니다."
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

    fun editPost() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true, message = "") }
            try {
                postRepository.editPost(postId, uiState.value.post)
                _uiState.update { state ->
                    state.copy(
                        isEditPostSuccess = true,
                        isLoading = false,
                        message = "게시글 편집이 완료되었습니다."
                    )
                }
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

    fun updateTitle(title: String) {
        _uiState.update { state ->
            val updatedPost = state.post.copy(subject = title)
            state.copy(
                post = updatedPost,
                createButtonEnabled = createPostCondition(state.copy(post = updatedPost))
            )
        }
    }

    fun updateContent(content: String) {
        _uiState.update { state ->
            val updatedPost = state.post.copy(content = content)
            state.copy(
                post = updatedPost,
                createButtonEnabled = createPostCondition(state.copy(post = updatedPost))
            )
        }
    }


    fun setMessageClear() {
        _uiState.update { state ->
            state.copy(message = "")
        }
    }

    private fun createPostCondition(
        state: CreatePostUIState
    ): Boolean = state.post.subject.isNotEmpty() && state.post.content.isNotEmpty()

}
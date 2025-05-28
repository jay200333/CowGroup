package com.example.home.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class CreatePostUIState(
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val post: Post = Post(
        id = 0,
        title = "",
        content = "",
        dateTime = "",
        userName = "",
        commentCount = 0,
        isRegistrant = false
    ),
    val createButtonEnabled: Boolean = false,
    val isCreatePostSuccess: Boolean = false,
    val isEditPostSuccess: Boolean = false,
    val message: String = ""
)

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState: MutableStateFlow<CreatePostUIState> = MutableStateFlow(CreatePostUIState())
    val uiState: StateFlow<CreatePostUIState> = _uiState.asStateFlow()
    private val eventId: Int = requireNotNull(savedStateHandle.get<Int>("eventId")) { "eventId is required." }
    private val postId: Int = requireNotNull(savedStateHandle.get<Int>("postId")) { "postId is required." }
    private val isEditMode: Boolean = requireNotNull(savedStateHandle.get<Boolean>("isEditMode")) { "isEditMode is required." }

    init {
        Log.d("CreatePostViewModel", "eventId: $eventId, postId: $postId, isEditMode: $isEditMode")
    }

    fun setMessageClear() {
        _uiState.update { state ->
            state.copy(message = "")
        }
    }

}
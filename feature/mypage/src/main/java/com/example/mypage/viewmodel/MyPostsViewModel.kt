package com.example.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyPostsUIState(
    val isLoading: Boolean = false,
    val message: String = "",
    val postList: List<Post> = emptyList()
)

@HiltViewModel
class MyPostsViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<MyPostsUIState> = MutableStateFlow(MyPostsUIState())
    val uiState: StateFlow<MyPostsUIState> = _uiState.asStateFlow()

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
}
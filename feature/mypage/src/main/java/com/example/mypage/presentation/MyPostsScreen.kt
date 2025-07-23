package com.example.mypage.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.CommentBottomSheetContent
import com.example.designsystem.component.CowGroupDialog
import com.example.designsystem.component.PagingPostItem
import com.example.model.Comment
import com.example.model.Post
import com.example.mypage.viewmodel.MyPostsCommentUIState
import com.example.mypage.viewmodel.MyPostsUIState
import com.example.mypage.viewmodel.MyPostsViewModel

@Composable
fun MyPostsScreen(
    viewModel: MyPostsViewModel = hiltViewModel(),
    onEditPostButtonClick: (Int, Int, Boolean) -> Unit,
    onNavigationButtonClick: () -> Unit
) {
    val uiState: MyPostsUIState by viewModel.uiState.collectAsState()
    val commentUIState: MyPostsCommentUIState by viewModel.commentUiState.collectAsState()

    MyPostsScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onEditPostButtonClick = onEditPostButtonClick,
        onDeletePostButtonClick = {},
        getCommentList = viewModel::getCommentList,
        updateComment = viewModel::updateComment,
        postComment = viewModel::postComment,
        deleteComment = viewModel::deleteComment,
        editComment = viewModel::editComment,
        switchEditMode = viewModel::switchEditMode,
        cancelEdit = viewModel::cancelEditMode,
        postList = uiState.postList,
        commentList = commentUIState.commentList,
        isEditMode = commentUIState.isEditMode,
        sendButtonEnabled = commentUIState.sendButtonEnabled,
        comment = commentUIState.comment
    )
}

@Composable
fun MyPostsScreen(
    onNavigationButtonClick: () -> Unit,
    onEditPostButtonClick: (Int, Int, Boolean) -> Unit,
    onDeletePostButtonClick: (Int) -> Unit,
    getCommentList: (Int) -> Unit,
    updateComment: (TextFieldValue) -> Unit,
    postComment: (Int) -> Unit,
    deleteComment: (Int?, Int) -> Unit,
    editComment: (Int) -> Unit,
    switchEditMode: (Int) -> Unit,
    cancelEdit: () -> Unit,
    postList: List<Post>,
    comment: TextFieldValue,
    commentList: List<Comment>,
    isEditMode: Boolean,
    sendButtonEnabled: Boolean
) {
    val sheetState = rememberModalBottomSheetState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedPostId by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "작성한 게시글 목록") },
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "icon_top_bar_nav",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (postList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 20.dp),
                        text = "작성한 게시글이 없어요.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "모임의 첫 게시글을 작성해 보세요.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(postList.size) { index ->
                        val post = postList[index]
                        PagingPostItem(
                            post = post,
                            onChatClick = {
                                selectedPostId = post.id
                            },
                            onDeleteButtonClick = {
                                selectedPostId = post.id
                                showDeleteDialog = true
                            },
                            onEditButtonClick = {
                                selectedPostId = 20
                                onEditPostButtonClick(0, selectedPostId!!, true)
                                selectedPostId = null
                            }
                        )
                        HorizontalDivider(
                            thickness = 5.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
    if (showDeleteDialog) {
        CowGroupDialog(
            title = "게시글 삭제",
            confirmButtonMessage = "확인",
            dismissButtonMessage = "취소",
            onDismissRequest = {
                showDeleteDialog = false
                selectedPostId = null
            },
            onDismiss = {
                showDeleteDialog = false
                selectedPostId = null
            },
            content = {
                Text(
                    text = "정말 게시글을 삭제하시겠습니까?",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            },
            onConfirm = {
                onDeletePostButtonClick(selectedPostId!!)
                showDeleteDialog = false
                selectedPostId = null
            }
        )
    }
    if (selectedPostId != null && !showDeleteDialog) {
        ModalBottomSheet(
            onDismissRequest = {
                selectedPostId = null
            },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            CommentBottomSheetContent(
                postId = selectedPostId!!,
                getCommentList = getCommentList,
                updateComment = updateComment,
                postComment = postComment,
                deleteComment = deleteComment,
                editComment = editComment,
                switchEditMode = switchEditMode,
                cancelEdit = cancelEdit,
                commentList = commentList,
                isEditMode = isEditMode,
                sendButtonEnabled = sendButtonEnabled,
                comment = comment
            )
        }
    }
}
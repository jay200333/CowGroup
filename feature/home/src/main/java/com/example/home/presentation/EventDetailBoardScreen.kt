package com.example.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.designsystem.component.CowGroupDialog
import com.example.home.R
import com.example.home.component.CommentBottomSheetContent
import com.example.home.component.EventDetailBoardItem
import com.example.home.viewmodel.EventDetailBoardUIState
import com.example.home.viewmodel.EventDetailBoardViewModel
import com.example.model.Post
import kotlinx.coroutines.flow.map

@Composable
fun EventDetailBoardScreen(
    viewModel: EventDetailBoardViewModel = hiltViewModel(),
    onCreatePostButtonClick: (Int, Int, Boolean) -> Unit,
    onEditPostButtonClick: (Int, Int, Boolean) -> Unit,
    eventId: Int,
    snackBarHostState: SnackbarHostState,
) {
    val uiState: EventDetailBoardUIState by viewModel.uiState.collectAsState()
    val pagingPosts = viewModel.uiState.map { it.postList }.collectAsLazyPagingItems()

    EventDetailBoardScreen(
        onCreatePostButtonClick = { onCreatePostButtonClick(eventId, 0, false) },
        onEditPostButtonClick = onEditPostButtonClick,
        onDeletePostButtonClick = viewModel::deletePost,
        postList = pagingPosts,
        snackBarHostState = snackBarHostState,
    )
    LaunchedEffect(uiState.message) {
        if (uiState.message.isNotEmpty()) {
            viewModel.setMessageClear()
            pagingPosts.refresh()
        }
    }

    LaunchedEffect(Unit) {
        pagingPosts.refresh()
    }
}

@Composable
fun EventDetailBoardScreen(
    onCreatePostButtonClick: () -> Unit,
    onEditPostButtonClick: (Int, Int, Boolean) -> Unit,
    onDeletePostButtonClick: (Int) -> Unit,
    postList: LazyPagingItems<Post>,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedPostId by remember { mutableIntStateOf(-1) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreatePostButtonClick,
                icon = {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(R.drawable.baseline_create_24),
                        contentDescription = "Fab_Event_Detail_HomeScreen",
                        tint = Color.White
                    )
                },
                text = {
                    Text(
                        text = "게시글 등록",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (postList.itemCount == 0) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "등록된 게시글이 없습니다.",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(postList.itemCount) { index ->
                        val post = postList[index]
                        if (post != null) {
                            EventDetailBoardItem(
                                post = post,
                                onChatClick = { showBottomSheet = true },
                                onDeleteButtonClick = {
                                    selectedPostId = post.id
                                    showDeleteDialog = true
                                },
                                onEditButtonClick = {
                                    selectedPostId = post.id
                                    onEditPostButtonClick(0, selectedPostId, true)
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
                onDismissRequest = { showDeleteDialog = false },
                onDismiss = { showDeleteDialog = false },
                content = {
                    Text(
                        text = "정말 게시글을 삭제하시겠습니까?",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                },
                onConfirm = {
                    onDeletePostButtonClick(selectedPostId)
                    showDeleteDialog = false
                }
            )
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                CommentBottomSheetContent()
                //val selectedMeeting = meetings.find{ it.id == selectedItemId}
            }
        }
    }
}
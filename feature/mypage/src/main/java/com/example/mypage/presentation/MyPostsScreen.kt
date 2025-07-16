package com.example.mypage.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.PagingPostItem
import com.example.model.Post
import com.example.mypage.viewmodel.MyPostsUIState
import com.example.mypage.viewmodel.MyPostsViewModel

@Composable
fun MyPostsScreen(
    viewModel: MyPostsViewModel = hiltViewModel(),
    onEditPostButtonClick: (Int, Int, Boolean) -> Unit,
    onNavigationButtonClick: () -> Unit
) {
    val uiState: MyPostsUIState by viewModel.uiState.collectAsState()

    MyPostsScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onEditPostButtonClick = onEditPostButtonClick,
        postList =  uiState.postList
    )
}

@Composable
fun MyPostsScreen(
    onNavigationButtonClick: () -> Unit,
    onEditPostButtonClick: (Int, Int, Boolean) -> Unit,
    postList: List<Post>
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
}
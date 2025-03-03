package com.example.mypage.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.PagingMeetingItem
import com.example.model.Event
import com.example.mypage.viewmodel.FullMeetingUIState
import com.example.mypage.viewmodel.FullMeetingViewModel

@Composable
fun FullMeetingScreen(
    viewModel: FullMeetingViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit
) {
    val uiState: FullMeetingUIState by viewModel.fullMeetingUIState.collectAsStateWithLifecycle()

    FullMeetingScreen(
        onEventClick = onEventClick,
        onNavigationButtonClick = onNavigationButtonClick,
        isBookmarkPage = uiState.isBookmarkPage,
        snackBarHostState = snackBarHostState,
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }
}

@Composable
fun FullMeetingScreen(
    onEventClick: (Int) -> Unit,
    onNavigationButtonClick: () -> Unit,
    isBookmarkPage: Boolean,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { if (isBookmarkPage) Text(text = "북마크 모임 목록") else Text(text = "참여 모임 목록") },
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "top_bar_nav_setting",
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(count = 10) {
                PagingMeetingItem(
                    event = Event(
                        id = 1,
                        name = "test1",
                        author = "홍길동",
                        content = "test1",
                        eventDate = "2025-12-7",
                        createdDate = "2025-03-01T12:06:33.445",
                        capacities = 10,
                        participants = 100,
                        isBookmarked = false,
                    ),
                    onEventClick = { onEventClick(123) }, onBookMarkClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun FullMeetingScreenPreview() {
    FullMeetingScreen(
        onEventClick = {},
        onNavigationButtonClick = {},
        isBookmarkPage = false
    )
}
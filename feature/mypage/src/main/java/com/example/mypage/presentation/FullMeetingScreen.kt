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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.designsystem.component.PagingMeetingItem
import com.example.model.Event
import com.example.mypage.viewmodel.FullMeetingUIState
import com.example.mypage.viewmodel.FullMeetingViewModel
import kotlinx.coroutines.flow.map

@Composable
fun FullMeetingScreen(
    viewModel: FullMeetingViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit
) {
    val uiState: FullMeetingUIState by viewModel.fullMeetingUIState.collectAsStateWithLifecycle()
    val pagingEvents = viewModel.fullMeetingUIState.map { it.eventList }.collectAsLazyPagingItems()

    FullMeetingScreen(
        onEventClick = onEventClick,
        onNavigationButtonClick = onNavigationButtonClick,
        onBookMarkClick = { eventId, isBookmarked ->
            viewModel.updateBookmark(
                eventId,
                isBookmarked
            )
        },
        isBookmarkPage = uiState.isBookmarkPage,
        eventList = pagingEvents,
        snackBarHostState = snackBarHostState,
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }

    LaunchedEffect(Unit) {
        pagingEvents.refresh()
    }
}

@Composable
fun FullMeetingScreen(
    onEventClick: (Int) -> Unit,
    onNavigationButtonClick: () -> Unit,
    onBookMarkClick: (Int, Boolean) -> Unit,
    isBookmarkPage: Boolean,
    eventList: LazyPagingItems<Event>,
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
            items(eventList.itemCount, key = eventList.itemKey { it.id }) { index ->
                val event = eventList[index]
                if (event != null) {
                    PagingMeetingItem(
                        event = event,
                        onEventClick = { onEventClick(event.id) },
                        onBookMarkClick = { onBookMarkClick(event.id, event.isBookmarked) },
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun FullMeetingScreenPreview() {
//    val pagingData = PagingData.from(emptyList<Event>())
//    FullMeetingScreen(
//        onEventClick = {},
//        onNavigationButtonClick = {},
//        eventList = LazyPagingItems(pagingData),
//        isBookmarkPage = false
//    )
//}
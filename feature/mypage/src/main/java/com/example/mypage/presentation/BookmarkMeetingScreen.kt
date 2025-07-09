package com.example.mypage.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.PagingMeetingItem
import com.example.model.Event
import com.example.mypage.viewmodel.BookmarkMeetingUIState
import com.example.mypage.viewmodel.BookmarkMeetingViewModel

@Composable
fun BookmarkMeetingScreen(
    viewModel: BookmarkMeetingViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit
) {
    val uiState: BookmarkMeetingUIState by viewModel.uiState.collectAsState()
    BookmarkMeetingScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onEventClick = { eventId -> onEventClick(eventId) },
        onBookMarkClick = { eventId, isBookmarked ->
            viewModel.updateBookmark(
                eventId,
                isBookmarked
            )
        },
        bookmarkEventList = uiState.bookmarkEventList
    )
}

@Composable
fun BookmarkMeetingScreen(
    onNavigationButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    onBookMarkClick: (Int, Boolean) -> Unit,
    bookmarkEventList: List<Event>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "관심 모임 목록(${bookmarkEventList.size})") },
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 24.dp)
        ) {
            if (bookmarkEventList.isNotEmpty()) {

                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(bookmarkEventList.size) { index ->
                        val bookmarkEvent = bookmarkEventList[index]
                        PagingMeetingItem(event = bookmarkEvent,
                            onEventClick = { onEventClick(bookmarkEvent.id) },
                            onBookMarkClick = {
                                onBookMarkClick(
                                    bookmarkEvent.id,
                                    bookmarkEvent.isBookmarked,
                                )
                            })
                    }
                }
            }
            else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 20.dp),
                            text = "관심 모임이 없어요.",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
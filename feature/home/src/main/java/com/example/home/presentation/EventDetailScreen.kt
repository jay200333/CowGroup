package com.example.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.MarkButton
import com.example.home.R
import com.example.home.viewmodel.EventDetailUIState
import com.example.home.viewmodel.EventDetailViewModel
import com.example.model.CreateEvent
import com.example.model.DetailEvent

@Composable
fun EventDetailScreen(
    viewModel: EventDetailViewModel = hiltViewModel(),
    onMemberButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
    onEditButtonClick: (Boolean, CreateEvent) -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    val uiState: EventDetailUIState by viewModel.eventDetailUIState.collectAsState()
    val event = with(uiState.detailEvent) {
        CreateEvent(
            name = name,
            category = category,
            location = location,
            eventDate = eventDate,
            capacity = capacity,
            content = content
        )
    }

    EventDetailScreen(
        onMemberButtonClick = onMemberButtonClick,
        onNavigationButtonClick = onNavigationButtonClick,
        onBookmarkButtonClick = { isBookmarked -> viewModel.updateBookmark(isBookmarked) },
        onJoinButtonClick = viewModel::joinEvent,
        onDeleteButtonClick = {},
        onEditButtonClick = { onEditButtonClick(true, event) },
        detailEvent = uiState.detailEvent,
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
fun EventDetailScreen(
    onMemberButtonClick: () -> Unit,
    onJoinButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
    onBookmarkButtonClick: (Boolean) -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    detailEvent: DetailEvent,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "top_bar_nav_icon_event_detail",
                        )
                    }
                },

                actions = {
                    Row {
                        IconButton(onClick = onDeleteButtonClick) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "btn_event_detail_delete"
                            )
                        }

                        IconButton(onClick = onEditButtonClick) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "btn_event_detail_edit"
                            )
                        }
                        MarkButton(
                            isMarked = detailEvent.isBookmarked,
                            onMarkClick = { onBookmarkButtonClick(detailEvent.isBookmarked) },
                            markedIconId = R.drawable.baseline_bookmarks_24,
                            unMarkedIconId = R.drawable.baseline_bookmarks_24,
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = detailEvent.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = detailEvent.category,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
            Text(text = detailEvent.eventDate, style = MaterialTheme.typography.labelMedium)
            Button(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.baseline_gps_fixed_24),
                    contentDescription = "btn_event_detail_map"
                )
                Text(text = "지도로 보기", modifier = Modifier.padding(start = 8.dp))
            }
            Text(text = detailEvent.content, style = MaterialTheme.typography.bodyMedium)
            Text(text = "정원 : ${detailEvent.capacity}명")
            Text(text = "참여 인원 : ${detailEvent.applicants}명")
            Button(onClick = onMemberButtonClick) {
                Text(text = "전체 참여자 목록 보기")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onJoinButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "참석 하기")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailPreview() {
    EventDetailScreen(
        onMemberButtonClick = {},
        onJoinButtonClick = {},
        onNavigationButtonClick = {},
        onBookmarkButtonClick = {},
        onDeleteButtonClick = {},
        onEditButtonClick = {},
        detailEvent = DetailEvent(
            id = 0,
            name = "test",
            author = "android",
            category = "Sports",
            createdDate = "2025-10-25",
            address = "주소",
            location = "장소",
            content = "내용",
            eventDate = "2025-10-28",
            capacity = 100,
            applicants = 20,
            isBookmarked = false
        )
    )
}

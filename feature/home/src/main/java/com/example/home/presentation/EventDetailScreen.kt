package com.example.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.CowGroupDialog
import com.example.designsystem.component.MarkButton
import com.example.designsystem.theme.CowGroupTheme
import com.example.home.R
import com.example.home.component.EditDropDownMenu
import com.example.home.viewmodel.EventDetailUIState
import com.example.home.viewmodel.EventDetailViewModel
import com.example.model.DetailEvent

@Composable
fun EventDetailScreen(
    viewModel: EventDetailViewModel = hiltViewModel(),
    onDeleteMeetingSuccess: () -> Unit,
    onMemberButtonClick: (Int) -> Unit,
    onNavigationButtonClick: () -> Unit,
    onEditButtonClick: (Int, Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    val uiState: EventDetailUIState by viewModel.eventDetailUIState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    EventDetailScreen(
        onMemberButtonClick = { eventId -> onMemberButtonClick(eventId) },
        onNavigationButtonClick = onNavigationButtonClick,
        onBookmarkButtonClick = { isBookmarked -> viewModel.updateBookmark(isBookmarked) },
        onJoinButtonClick = viewModel::updateJoinEvent,
        onDeleteButtonClick = { showDialog = true },
        onEditButtonClick = { onEditButtonClick(uiState.detailEvent.id, true) },
        detailEvent = uiState.detailEvent,
        showDialog = showDialog,
        onDismissDialog = { showDialog = false },
        onConfirmDialog = {
            viewModel.deleteEvent()
            showDialog = false
        },
        snackBarHostState = snackBarHostState,
    )
    LaunchedEffect(uiState.isDeleteSuccess) {
        if (uiState.isDeleteSuccess) {
            onDeleteMeetingSuccess()
            viewModel.setDeleteState(false)
            return@LaunchedEffect
        }
    }

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }

    LaunchedEffect(uiState.detailEvent.isParticipated) {
        viewModel.getEventDetail()
    }

    LaunchedEffect(Unit) {
        viewModel.getEventDetail()
    }
}

@Composable
fun EventDetailScreen(
    onMemberButtonClick: (Int) -> Unit,
    onJoinButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
    onBookmarkButtonClick: (Boolean) -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    detailEvent: DetailEvent,
    showDialog: Boolean,
    onDismissDialog: () -> Unit,
    onConfirmDialog: () -> Unit,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("홈", "게시판")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "모임 채널",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (detailEvent.editRights) {
                            IconButton(onClick = onEditButtonClick) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "수정하기"
                                )
                            }
                            IconButton(onClick = onDeleteButtonClick) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "삭제하기"
                                )
                            }
                        }
                        MarkButton(
                            isMarked = detailEvent.isBookmarked,
                            onMarkClick = { onBookmarkButtonClick(detailEvent.isBookmarked) },
                            markedIconId = R.drawable.baseline_bookmarks_24,
                            unMarkedIconId = R.drawable.baseline_bookmarks_24,
                        )
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "공유하기"
                            )
                        }
                        EditDropDownMenu()
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        if (showDialog) {
            CowGroupDialog(
                title = "모임 삭제",
                confirmButtonMessage = "확인",
                dismissButtonMessage = "취소",
                onDismissRequest = onDismissDialog,
                onDismiss = onDismissDialog,
                content = {
                    Text(
                        text = "정말 모임을 삭제하시겠습니까?",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                },
                onConfirm = onConfirmDialog
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            SecondaryTabRow(selectedTabIndex = state) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(text = title) },
                        selected = state == index,
                        onClick = { state = index })
                }
            }
            when (state) {
                0 -> EventDetailHomeScreen { onMemberButtonClick(detailEvent.id) }
                1 -> EventDetailBoardScreen()
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = detailEvent.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = detailEvent.category,
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.Gray
                        )
                    }
                    Text(
                        text = detailEvent.author,
                        style = MaterialTheme.typography.labelLarge,
                    )
                    Text(
                        text = "모임 날짜",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = detailEvent.eventDate,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "모임 장소",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = detailEvent.location, style = MaterialTheme.typography.bodyMedium)
                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_gps_fixed_24),
                            contentDescription = "btn_event_detail_map"
                        )
                        Text(text = "지도로 보기", modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        "모임 내용",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = detailEvent.content, style = MaterialTheme.typography.bodyLarge)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "정원 : ${detailEvent.capacity}명",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "참여 인원 : ${detailEvent.applicants}명",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Button(
                onClick = { onMemberButtonClick(detailEvent.id) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.filledTonalButtonColors()
            ) {
                Text(text = "전체 참여자 목록 보기")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onJoinButtonClick,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (detailEvent.isParticipated) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            ) {
                Text(text = if (detailEvent.isParticipated) "나가기" else "참석하기", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailPreview() {
    CowGroupTheme {
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
                location = "장소",
                content = "내용",
                eventDate = "2025-10-28",
                capacity = 100,
                applicants = 20,
                isBookmarked = false,
                editRights = false,
                isParticipated = false
            ),
            showDialog = false,
            onDismissDialog = {},
            onConfirmDialog = {}
        )
    }
}

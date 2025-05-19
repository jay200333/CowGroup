package com.example.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.CowGroupDialog
import com.example.designsystem.component.MarkButton
import com.example.designsystem.theme.CowGroupTheme
import com.example.home.R
import com.example.home.component.DropDownMenuItem
import com.example.home.component.EditDropDownMenu
import com.example.home.viewmodel.EventDetailUIState
import com.example.home.viewmodel.EventDetailViewModel
import com.example.model.DetailEvent

@Composable
fun EventDetailScreen(
    viewModel: EventDetailViewModel = hiltViewModel(),
    onDeleteMeetingSuccess: () -> Unit,
    onMemberButtonClick: (Int) -> Unit,
    onRegularMemberButtonClick: (Int) -> Unit,
    onNavigationButtonClick: () -> Unit,
    onEditButtonClick: (Int, Boolean) -> Unit,
    onCreateRegularMeetingButtonClick: (Int, Boolean, Int) -> Unit,
    onEditRegularMeetingButtonClick: (Int, Boolean, Int) -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    val uiState: EventDetailUIState by viewModel.eventDetailUIState.collectAsState()
    var showDialog: DialogType? by remember { mutableStateOf(null) }

    EventDetailScreen(
        onMemberButtonClick = { eventId -> onMemberButtonClick(eventId) },
        onRegularMemberButtonClick = onRegularMemberButtonClick,
        onNavigationButtonClick = onNavigationButtonClick,
        onBookmarkButtonClick = { isBookmarked -> viewModel.updateBookmark(isBookmarked) },
        onCreateRegularMeetingButtonClick = onCreateRegularMeetingButtonClick,
        onEditRegularMeetingButtonClick = { eventId, isEditMode, regularId ->
            onEditRegularMeetingButtonClick(
                eventId,
                isEditMode,
                regularId
            )
        },
        onJoinButtonClick = viewModel::updateJoinEvent,
        onJoinRegularMeetingClick = { regularEventId ->
            viewModel.updateJoinRegularMeeting(
                regularEventId
            )
        },
        onDeleteButtonClick = { showDialog = DialogType.DeleteEvent },
        onExitButtonClick = { showDialog = DialogType.LeaveEvent },
        onEditButtonClick = { onEditButtonClick(uiState.detailEvent.id, true) },
        detailEvent = uiState.detailEvent,
        showDialog = showDialog,
        onDismissDialog = { showDialog = null },
        onConfirmDialog = {
            when (showDialog) {
                is DialogType.DeleteEvent -> {
                    viewModel.deleteEvent()
                    showDialog = null
                }

                is DialogType.LeaveEvent -> {
                    viewModel.updateJoinEvent()
                    showDialog = null
                }

                null -> {}
            }
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
}

@Composable
fun EventDetailScreen(
    onMemberButtonClick: (Int) -> Unit,
    onRegularMemberButtonClick: (Int) -> Unit,
    onJoinButtonClick: () -> Unit,
    onJoinRegularMeetingClick: (Int) -> Unit,
    onNavigationButtonClick: () -> Unit,
    onBookmarkButtonClick: (Boolean) -> Unit,
    onCreateRegularMeetingButtonClick: (Int, Boolean, Int) -> Unit,
    onEditRegularMeetingButtonClick: (Int, Boolean, Int) -> Unit,
    onDeleteButtonClick: () -> Unit,
    onExitButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    detailEvent: DetailEvent,
    showDialog: DialogType?,
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
                        if (detailEvent.eventRegistrant) {
                            EditDropDownMenu(
                                menuItems = listOf(
                                    DropDownMenuItem("수정하기", onEditButtonClick),
                                    DropDownMenuItem("삭제하기", onDeleteButtonClick)
                                )
                            )
                        } else if (detailEvent.isParticipated) {
                            EditDropDownMenu(
                                menuItems = listOf(
                                    DropDownMenuItem("모임 나가기", onExitButtonClick)
                                )
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        if (showDialog != null) {
            when (showDialog) {
                is DialogType.DeleteEvent -> {
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

                is DialogType.LeaveEvent -> {
                    CowGroupDialog(
                        title = "모임 나가기",
                        confirmButtonMessage = "확인",
                        dismissButtonMessage = "취소",
                        onDismissRequest = onDismissDialog,
                        onDismiss = onDismissDialog,
                        content = {
                            Text(
                                text = "정말 모임을 나가시겠습니까?",
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        },
                        onConfirm = onConfirmDialog
                    )
                }
            }
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
                0 -> EventDetailHomeScreen(detailEvent = detailEvent,
                    onMemberButtonClick = { onMemberButtonClick(detailEvent.id) },
                    onJoinButtonClick = { onJoinButtonClick() },
                    onCreateRegularMeetingButtonClick = { onCreateRegularMeetingButtonClick(0, false, 0) },
                    onEditRegularMeetingButtonClick = onEditRegularMeetingButtonClick,
                    onJoinRegularMeetingClick = { regularEventId -> onJoinRegularMeetingClick(regularEventId) },
                    onRegularMemberButtonClick = onRegularMemberButtonClick
                )

                1 -> EventDetailBoardScreen()
            }
        }
    }
}

sealed class DialogType {
    object DeleteEvent : DialogType()
    object LeaveEvent : DialogType()
}

@Preview(showBackground = true)
@Composable
fun EventDetailPreview() {
    CowGroupTheme {
        EventDetailScreen(
            onMemberButtonClick = {},
            onRegularMemberButtonClick = {},
            onJoinButtonClick = {},
            onNavigationButtonClick = {},
            onBookmarkButtonClick = {},
            onCreateRegularMeetingButtonClick = { _, _, _ -> },
            onEditRegularMeetingButtonClick = { _, _, _ -> },
            onDeleteButtonClick = {},
            onExitButtonClick = {},
            onEditButtonClick = {},
            detailEvent = DetailEvent(
                id = 0,
                name = "test",
                category = "Sports",
                content = "내용",
                capacity = 100,
                applicants = 20,
                isBookmarked = false,
                url = "",
                eventRegistrant = false,
                isParticipated = false,
                regularEvents = emptyList()
            ),
            showDialog = null,
            onDismissDialog = {},
            onConfirmDialog = {},
            onJoinRegularMeetingClick = {}
        )
    }
}

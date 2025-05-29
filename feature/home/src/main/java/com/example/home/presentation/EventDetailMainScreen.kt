package com.example.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.CowGroupDialog
import com.example.designsystem.component.MarkButton
import com.example.home.R
import com.example.home.component.DropDownMenuItem
import com.example.home.component.EditDropDownMenu
import com.example.home.viewmodel.EventDetailMainUIState
import com.example.home.viewmodel.EventDetailMainViewModel
import com.example.model.DetailEvent

@Composable
fun EventDetailMainScreen(
    viewModel: EventDetailMainViewModel = hiltViewModel(),
    onDeleteMeetingSuccess: () -> Unit,
    onNavigationButtonClick: () -> Unit,
    onEditButtonClick: (Int, Boolean) -> Unit,
    onMemberButtonClick: (Int) -> Unit,
    onShowFullRegularMeetingClick: (Int) -> Unit,
    onCreateRegularMeetingButtonClick: (Int, Boolean, Int) -> Unit,
    onRegularMemberButtonClick: (Int) -> Unit,
    onEditRegularMeetingButtonClick: (Int, Boolean, Int) -> Unit,
    onCreatePostButtonClick: (Int, Int, Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit
) {
    val uiState: EventDetailMainUIState by viewModel.uiState.collectAsState()
    var showDialog: DialogType? by remember { mutableStateOf(null) }

    EventDetailMainScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onEditButtonClick = { onEditButtonClick(uiState.detailEvent.id, true) },
        onBookmarkButtonClick = viewModel::updateBookmark,
        onDeleteButtonClick = { showDialog = DialogType.DeleteEvent },
        onExitButtonClick = { showDialog = DialogType.LeaveEvent },
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
        onMemberButtonClick = onMemberButtonClick,
        onShowFullRegularMeetingClick = onShowFullRegularMeetingClick,
        onCreateRegularMeetingButtonClick = onCreateRegularMeetingButtonClick,
        onRegularMemberButtonClick = onRegularMemberButtonClick,
        onEditRegularMeetingButtonClick = onEditRegularMeetingButtonClick,
        onCreatePostButtonClick = onCreatePostButtonClick,
        snackBarHostState = snackBarHostState
    )
    LaunchedEffect(uiState.isDeleteSuccess) {
        if (uiState.isDeleteSuccess) {
            onDeleteMeetingSuccess()
            viewModel.setDeleteState(false)
            return@LaunchedEffect
        }
    }

    LaunchedEffect(uiState.message) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }
}

@Composable
fun EventDetailMainScreen(
    onNavigationButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    onBookmarkButtonClick: (Boolean) -> Unit,
    onDeleteButtonClick: () -> Unit,
    onExitButtonClick: () -> Unit,
    detailEvent: DetailEvent,
    showDialog: DialogType?,
    onConfirmDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    onMemberButtonClick: (Int) -> Unit,
    onShowFullRegularMeetingClick: (Int) -> Unit,
    onCreateRegularMeetingButtonClick: (Int, Boolean, Int) -> Unit,
    onRegularMemberButtonClick: (Int) -> Unit,
    onEditRegularMeetingButtonClick: (Int, Boolean, Int) -> Unit,
    onCreatePostButtonClick: (Int, Int, Boolean) -> Unit,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    var currentTab by remember { mutableIntStateOf(0) }
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
            SecondaryTabRow(selectedTabIndex = currentTab) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(text = title) },
                        selected = currentTab == index,
                        onClick = { currentTab = index })
                }
            }

            when (currentTab) {
                0 -> EventDetailHomeScreen(
                    eventId = detailEvent.id,
                    onMemberButtonClick = { onMemberButtonClick(detailEvent.id) },
                    onShowFullRegularMeetingClick = { onShowFullRegularMeetingClick(detailEvent.id) },
                    onCreateRegularMeetingButtonClick = onCreateRegularMeetingButtonClick,
                    onRegularMemberButtonClick = onRegularMemberButtonClick,
                    onEditRegularMeetingButtonClick = onEditRegularMeetingButtonClick,
                )

                1 -> EventDetailBoardScreen(
                    eventId = detailEvent.id,
                    onCreatePostButtonClick = onCreatePostButtonClick,
                    snackBarHostState = snackBarHostState,
                )
            }
        }
    }
}

sealed class DialogType {
    object DeleteEvent : DialogType()
    object LeaveEvent : DialogType()
}
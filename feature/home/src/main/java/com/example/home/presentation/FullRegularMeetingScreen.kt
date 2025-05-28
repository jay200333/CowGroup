package com.example.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.home.component.RegularMeetingBottomSheetContent
import com.example.home.component.RegularMeetingItem
import com.example.home.viewmodel.FullRegularMeetingUIState
import com.example.home.viewmodel.FullRegularMeetingViewModel
import com.example.model.RegularEvent
import kotlinx.coroutines.flow.map

@Composable
fun FullRegularMeetingScreen(
    viewModel: FullRegularMeetingViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onEditRegularMeetingButtonClick: (Int, Boolean, Int) -> Unit,
    onRegularMemberButtonClick: (Int) -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    val eventId = viewModel.getEventId()
    val uiState: FullRegularMeetingUIState by viewModel.uiState.collectAsState()
    val pagingRegularMeetings =
        viewModel.uiState.map { it.regularMeetingList }.collectAsLazyPagingItems()

    FullRegularMeetingScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        regularEventList = pagingRegularMeetings,
        onJoinRegularEvent = viewModel::updateJoinRegularMeeting,
        onEditRegularMeetingButtonClick = onEditRegularMeetingButtonClick,
        onDeleteRegularMeetingButtonClick = viewModel::deleteRegularMeeting,
        onRegularMemberButtonClick = onRegularMemberButtonClick,
        eventId = eventId,
        snackBarHostState = snackBarHostState,
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }

    LaunchedEffect(Unit) {
        pagingRegularMeetings.refresh()
    }
}

@Composable
fun FullRegularMeetingScreen(
    onNavigationButtonClick: () -> Unit,
    onJoinRegularEvent: (Int, Int?) -> Unit,
    regularEventList: LazyPagingItems<RegularEvent>,
    onEditRegularMeetingButtonClick: (Int, Boolean, Int) -> Unit,
    onDeleteRegularMeetingButtonClick: (Int) -> Unit,
    onRegularMemberButtonClick: (Int) -> Unit,
    eventId: Int,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedEvent by remember { mutableStateOf<RegularEvent?>(null) }

    if (showBottomSheet && selectedEvent != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
                selectedEvent = null
            },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            RegularMeetingBottomSheetContent(
                regularEvent = selectedEvent!!,
                onAttendButtonClick = {
                    onJoinRegularEvent(selectedEvent!!.id, selectedEvent!!.participationId)
                    showBottomSheet = false
                },
                onEditButtonClick = {
                    onEditRegularMeetingButtonClick(
                        eventId,
                        true,
                        selectedEvent!!.id
                    )
                    showBottomSheet = false
                },
                onDeleteButtonClick = {
                    onDeleteRegularMeetingButtonClick(selectedEvent!!.id)
                    showBottomSheet = false
                    regularEventList.refresh()
                },
                onMemberButtonClick = {
                    onRegularMemberButtonClick(selectedEvent!!.id)
                    showBottomSheet = false
                })
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "정기 모임") },
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "top_bar_nav_member",
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(regularEventList.itemCount) { index ->
                    val regularEvent = regularEventList[index]
                    if (regularEvent != null) {
                        RegularMeetingItem(
                            regularEvent = regularEvent,
                            onItemClick = {
                                selectedEvent = regularEvent
                                showBottomSheet = true
                            },
                            onJoinRegularEvent = {
                                onJoinRegularEvent(
                                    regularEvent.id,
                                    regularEvent.participationId
                                )
                            })
                    }
                }
            }
        }
    }
}
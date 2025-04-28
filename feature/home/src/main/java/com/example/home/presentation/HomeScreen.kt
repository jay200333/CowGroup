package com.example.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.data.model.SearchHistory
import com.example.designsystem.component.PagingMeetingItem
import com.example.home.component.HomeScreenSearchBar
import com.example.home.viewmodel.HomeUIState
import com.example.home.viewmodel.HomeViewModel
import com.example.model.Event
import kotlinx.coroutines.flow.map

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onLogoutButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    onCreateMeetingClick: (Int, Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    val uiState: HomeUIState by viewModel.homeUIState.collectAsState()
    val pagingEvents = viewModel.homeUIState.map { it.eventList }.collectAsLazyPagingItems()
    val recentSearches by viewModel.getRecentSearches().collectAsState(initial = emptyList())


    HomeScreen(
        onSearchButtonClick = viewModel::insertQuery,
        onDeleteSearchHistoryButtonClick = viewModel::deleteSearchHistory,
        onLogoutButtonClick = viewModel::logout,
        onEventClick = { eventId -> onEventClick(eventId) },
        onCreateMeetingClick = { onCreateMeetingClick(0, false) },
        onBookMarkClick = { eventId, isBookmarked ->
            viewModel.updateBookmark(
                eventId,
                isBookmarked
            )
        },
        eventList = pagingEvents,
        recentSearches = recentSearches,
        snackBarHostState = snackBarHostState,
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }

    if (uiState.isLogout) {
        onLogoutButtonClick()
    }

    LaunchedEffect(Unit) {
        pagingEvents.refresh()
    }
}

@Composable
fun HomeScreen(
    onSearchButtonClick: (String) -> Unit,
    onDeleteSearchHistoryButtonClick: (String) -> Unit,
    onLogoutButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    onCreateMeetingClick: () -> Unit,
    onBookMarkClick: (Int, Boolean) -> Unit,
    eventList: LazyPagingItems<Event>,
    recentSearches: List<SearchHistory>,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val listState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeScreenSearchBar(
                onSearchButtonClick,
                onDeleteSearchHistoryButtonClick,
                onLogoutButtonClick,
                recentSearches,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateMeetingClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Fab_HomeScreen",
                )
            }
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
            if (eventList.itemCount != 0) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = listState
                ) {
                    items(eventList.itemCount, key = eventList.itemKey { it.id })
                    { index ->
                        val event = eventList[index]
                        if (event != null) {
                            PagingMeetingItem(
                                event = event,
                                onEventClick = { onEventClick(event.id) },
                                onBookMarkClick = {
                                    onBookMarkClick(
                                        event.id,
                                        event.isBookmarked,
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(
//        onLogoutButtonClick = {},
//        onEventClick = {},
//        onCreateMeetingClick = {},
//        onBookMarkClick = { _, _ -> },
//        eventList = listOf(
//            Event(1, "test1", "test1", "2025-12-7", "2024-12-31", 10, 100, false),
//            Event(2, "test2", "test2", "2025-11-3", "2024-12-30", 20, 200, true),
//            Event(3, "test3", "test3", "2025-12-2", "2024-12-29", 30, 300, true),
//        ),
//    )
//}

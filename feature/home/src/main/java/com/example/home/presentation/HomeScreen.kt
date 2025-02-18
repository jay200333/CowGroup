package com.example.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.home.R
import com.example.home.component.HomeItem
import com.example.home.component.HomeScreenSearchBar
import com.example.home.viewmodel.HomeUIState
import com.example.home.viewmodel.HomeViewModel
import com.example.model.Event

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onLogoutButtonClick: () -> Unit,
    onEventClick: () -> Unit,
    onCreateMeetingClick: () -> Unit,
) {
    val uiState: HomeUIState by viewModel.homeUIState.collectAsStateWithLifecycle()

    HomeScreen(
        onLogoutButtonClick = viewModel::logout,
        onEventClick = onEventClick,
        onCreateMeetingClick = onCreateMeetingClick,
        onBookMarkClick = { eventId, isBookmarked ->
            viewModel.updateBookmark(eventId, isBookmarked)
        },
        eventList = uiState.eventList,
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState = rememberUpdatedState(lifecycleOwner.lifecycle.currentState)

    DisposableEffect(lifecycleState) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getEvents()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (uiState.isLogout) {
        onLogoutButtonClick()
    }
}

@Composable
fun HomeScreen(
    onLogoutButtonClick: () -> Unit,
    onEventClick: () -> Unit,
    onCreateMeetingClick: () -> Unit,
    onBookMarkClick: (Int, Boolean) -> Unit,
    eventList: List<Event>,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeScreenSearchBar(onLogoutButtonClick, R.drawable.baseline_login_24) },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateMeetingClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Fab_HomeScreen",
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                itemsIndexed(
                    items = eventList,
                    key = { _, event -> event.id },
                ) { _, event ->
                    HomeItem(
                        event = event,
                        onEventClick = onEventClick,
                        onBookMarkClick = { isBookmarked ->
                            onBookMarkClick(
                                event.id,
                                isBookmarked,
                            )
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onLogoutButtonClick = {},
        onEventClick = {},
        onCreateMeetingClick = {},
        onBookMarkClick = { _, _ -> },
        eventList = listOf(
            Event(1, "test1", "test1", "2025-12-7", "2024-12-31", 10, 100, false),
            Event(2, "test2", "test2", "2025-11-3", "2024-12-30", 20, 200, true),
            Event(3, "test3", "test3", "2025-12-2", "2024-12-29", 30, 300, true),
        ),
    )
}

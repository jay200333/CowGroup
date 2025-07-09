package com.example.mypage.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.model.Event
import com.example.mypage.component.JoinMeetingItem
import com.example.mypage.viewmodel.JoinMeetingUIState
import com.example.mypage.viewmodel.JoinMeetingViewModel

@Composable
fun JoinMeetingScreen(
    viewModel: JoinMeetingViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit
) {
    val uiState: JoinMeetingUIState by viewModel.uiState.collectAsState()

    JoinMeetingScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onEventClick = onEventClick,
        eventList = uiState.eventList
    )
}

@Composable
fun JoinMeetingScreen(
    onNavigationButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    eventList: List<Event>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "가입한 모임 목록(${eventList.size})") },
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
        Column(modifier = Modifier.padding(innerPadding).padding(top = 24.dp)) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(eventList.size) { index ->
                    val event = eventList[index]
                    JoinMeetingItem(event) { onEventClick(event.id) }
                }
            }
        }
    }
}
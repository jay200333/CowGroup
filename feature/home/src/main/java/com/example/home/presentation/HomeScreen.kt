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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.home.R
import com.example.home.component.HomeItem
import com.example.home.component.HomeScreenSearchBar
import com.example.home.viewmodel.HomeUIState
import com.example.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onLoginButtonClick: () -> Unit,
    onEventClick: () -> Unit,
    onCreateMeetingClick: () -> Unit,
) {
    val uiState: HomeUIState by viewModel.homeUIState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { viewModel.initViewModel() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeScreenSearchBar(onLoginButtonClick, R.drawable.baseline_login_24) },
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
                    items = uiState.eventList,
                    key = { _, event -> event.id },
                ) { _, event ->
                    HomeItem(
                        event = event,
                        onEventClick = onEventClick,
                        onBookMarkClick = { isBookmarked ->
                            viewModel.updateBookmark(
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
        onLoginButtonClick = {},
        onEventClick = {},
        onCreateMeetingClick = {},
    )
}

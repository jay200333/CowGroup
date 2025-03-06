package com.example.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.home.component.MemberItem
import com.example.home.viewmodel.MemberUIState
import com.example.home.viewmodel.MemberViewModel

@Composable
fun MemberScreen(
    viewModel: MemberViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    val uiState: MemberUIState by viewModel.memberUIState.collectAsState()

    MemberScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        snackBarHostState = snackBarHostState,
    )
}

@Composable
fun MemberScreen(
    onNavigationButtonClick: () -> Unit,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "참여자 목록") },
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
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(count = 10) {
                    MemberItem("male")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemberScreenPreview() {
    MemberScreen(
        onNavigationButtonClick = {},
    )
}

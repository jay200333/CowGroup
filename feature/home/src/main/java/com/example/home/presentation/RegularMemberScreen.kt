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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.theme.CowGroupTheme
import com.example.home.component.MemberItem
import com.example.home.viewmodel.RegularMeetingMemberUIState
import com.example.home.viewmodel.RegularMeetingMemberViewModel
import com.example.model.EventMember
import com.example.model.MemberInfo

@Composable
fun RegularMemberScreen(
    viewModel: RegularMeetingMemberViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit
) {
    val uiState: RegularMeetingMemberUIState by viewModel.regularMemberUIState.collectAsState()

    RegularMemberScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        regularMember = uiState.regularMeetingMember,
        snackBarHostState = snackBarHostState,
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }

    LaunchedEffect(Unit){
        viewModel.getMemberList()
    }
}

@Composable
fun RegularMemberScreen(
    onNavigationButtonClick: () -> Unit,
    regularMember: EventMember,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "모임 인원(${regularMember.memberCount})") },
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
                items(regularMember.memberInfoList.size) { index ->
                    val memberInfo = regularMember.memberInfoList[index]
                    MemberItem(memberInfo)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegularMemberScreenPreview() {
    CowGroupTheme {
        RegularMemberScreen(
            onNavigationButtonClick = {},
            regularMember = EventMember(
                memberInfoList = listOf(MemberInfo("안드로이드", "ESTP")), memberCount = 1
            )
        )
    }
}
package com.example.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    onLoginButtonClick: () -> Unit,
    onEventClick: () -> Unit,
    onCreateMeetingClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "HomeScreen", style = MaterialTheme.typography.displayLarge)
            // topBar 구현 해야함
            Button(
                onClick = onLoginButtonClick,
            ) {
                Text(text = "로그인 버튼")
            }
            Button(onClick = onEventClick) {
                Text(text = "이벤트 클릭")
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

package com.example.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.MarkButton
import com.example.home.R

@Composable
fun EventDetailScreen(
    onMemberButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
) {
    var isBookMarked by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "top_bar_nav_icon_event_detail",
                        )
                    }
                },
                // bookMark 상태 값 반영해야함
                actions = {
                    MarkButton(
                        isMarked = true,
                        onMarkClick = { isBookMarked = it },
                        markedIconId = R.drawable.baseline_bookmarks_24,
                        unMarkedIconId = R.drawable.baseline_bookmarks_24,
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "EventDetailScreen", style = MaterialTheme.typography.displaySmall)
            Button(onClick = {}) {
                Text(text = "지도로 보기")
            }
            Button(onClick = onMemberButtonClick) {
                Text(text = "전체 참여자 목록 보기")
            }
            Button(onClick = {}) {
                Text(text = "참석 하기")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailPreview() {
    EventDetailScreen(
        onMemberButtonClick = {},
        onNavigationButtonClick = {},
    )
}

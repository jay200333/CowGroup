package com.example.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.MarkButton
import com.example.home.R

@Composable
fun EventDetailScreen(
    onMemberButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
) {
    var isBookMarked by remember { mutableStateOf(false) }
    val totalMember: Int = 10
    val currentMember: Int = 5

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
                .padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "이벤트 제목",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(text = "카테고리1", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(text = "2024-12-27.01:22", style = MaterialTheme.typography.labelMedium)
            Button(onClick = {}) {
                Icon(painter = painterResource(R.drawable.baseline_gps_fixed_24), contentDescription = "btn_event_detail_map")
                Text(text = "지도로 보기", modifier = Modifier.padding(start = 8.dp))
            }
            Text(text = "같이 운동하실 분 구합니다.\n같이 프레스 운동하면서 서로 보조해주실 분 구합니다.", style = MaterialTheme.typography.bodyMedium)
            Text(text = "정원 : ${totalMember}명")
            Text(text = "참여 인원 : ${currentMember}명")
            Button(onClick = onMemberButtonClick) {
                Text(text = "전체 참여자 목록 보기")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
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

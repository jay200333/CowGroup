package com.example.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.home.component.CowGroupSlider
import com.example.home.component.DatePickerTextField

@Composable
fun CreateMeetingScreen(
    onNavigationButtonClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "모임 만들기")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "top_bar_nav_icon_create_meeting",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "모임 이름",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "모임 이름을 입력하세요.") },
                placeholder = { Text(text = "모임 이름") },
                singleLine = true,
            )
            Text(
                text = "카테고리 선택",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "카테고리를 입력하세요.") },
                placeholder = { Text(text = "카테고리") },
                singleLine = true,
            )
            Text(
                text = "모임 장소",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "모임 장소를 입력하세요.") },
                placeholder = { Text(text = "모임 장소") },
                singleLine = true,
            )
            Text(
                text = "모임 날짜",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            DatePickerTextField(
                date = "2023-10-01",
                onDateSelected = {},
            )
            Text(
                text = "정원",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            CowGroupSlider(
                value = 1f,
                steps = 100,
                valueRange = 1f..100f,
                onValueChange = {},
            )

            Text(
                text = "상세 내용",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp).verticalScroll(scrollState),
                label = { Text(text = "상세 내용을 입력하세요.") },
                placeholder = { Text(text = "상세 내용") },
            )
            Button(onClick = {}, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Text(text = "모임 만들기 버튼")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateMeetingScreenPreview() {
    CreateMeetingScreen(
        onNavigationButtonClick = {},
    )
}

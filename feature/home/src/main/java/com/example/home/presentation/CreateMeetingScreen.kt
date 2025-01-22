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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.home.component.CowGroupSlider
import com.example.home.component.DatePickerTextField
import com.example.home.viewmodel.CreateMeetingUIState
import com.example.home.viewmodel.CreateMeetingViewModel
import com.example.model.DetailEvent

@Composable
fun CreateMeetingScreen(
    viewModel: CreateMeetingViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onCreateMeetingSuccess: () -> Unit,
    onEditMeetingSuccess: () -> Unit,
) {
    val uiState: CreateMeetingUIState by viewModel.createMeetingUIState.collectAsStateWithLifecycle()

    CreateMeetingScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onCreateButtonClick = viewModel::createMeeting,
        onEditButtonClick = viewModel::editMeeting,
        updateName = { name -> viewModel.updateName(name) },
        updateCategory = { category -> viewModel.updateCategory(category) },
        updateLocation = { location -> viewModel.updateLocation(location) },
        updateEventDate = { eventDate -> viewModel.updateEventDate(eventDate) },
        updateCapacity = { capacity -> viewModel.updateCapacity(capacity) },
        updateContent = { content -> viewModel.updateContent(content) },
        detailEvent = uiState.detailEvent,
        isEditMode = uiState.isEditMode,
    )

    if (uiState.isCreateMeetingSuccess) {
        LaunchedEffect(Unit) {
            onCreateMeetingSuccess()
        }
    }

    if (uiState.isEditMeetingSuccess) {
        LaunchedEffect(Unit) {
            onEditMeetingSuccess()
        }
    }
}

@Composable
fun CreateMeetingScreen(
    onNavigationButtonClick: () -> Unit,
    onCreateButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    updateName: (String) -> Unit,
    updateCategory: (String) -> Unit,
    updateLocation: (String) -> Unit,
    updateEventDate: (String) -> Unit,
    updateCapacity: (Float) -> Unit,
    updateContent: (String) -> Unit,
    detailEvent: DetailEvent,
    isEditMode: Boolean,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (isEditMode) {
                        Text(text = "모임 편집")
                    } else {
                        Text(text = "모임 만들기")
                    }
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
                value = detailEvent.name,
                onValueChange = updateName,
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
                value = detailEvent.category,
                onValueChange = updateCategory,
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
                value = detailEvent.location,
                onValueChange = updateLocation,
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
                date = detailEvent.eventDate,
                onDateSelected = updateEventDate,
            )
            Text(
                text = "정원",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            CowGroupSlider(
                value = detailEvent.capacity.toFloat(),
                steps = 20,
                valueRange = 5f..100f,
                onValueChange = updateCapacity,
            )

            Text(
                text = "상세 내용",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = detailEvent.content,
                onValueChange = updateContent,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .verticalScroll(scrollState),
                label = { Text(text = "상세 내용을 입력하세요.") },
                placeholder = { Text(text = "상세 내용") },
            )
            if (isEditMode) {
                Button(
                    onClick = onEditButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                ) {
                    Text(text = "편집 완료")
                }
            } else {
                Button(
                    onClick = onCreateButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                ) {
                    Text(text = "모임 생성")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateMeetingScreenPreview() {
    CreateMeetingScreen(
        onNavigationButtonClick = {},
        onCreateButtonClick = {},
        onEditButtonClick = {},
        updateName = {},
        updateCategory = {},
        updateLocation = {},
        updateEventDate = {},
        updateCapacity = {},
        updateContent = {},
        isEditMode = false,
        detailEvent = DetailEvent(
            name = "롤",
            category = "게임",
            location = "소환사의 협곡",
            eventDate = "2025-01-19",
            capacity = 80,
            content = "text content",
        ),
    )
}

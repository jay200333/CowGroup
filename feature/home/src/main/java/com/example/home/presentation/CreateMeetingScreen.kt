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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.DateUtil.todayStartOfDayMillis
import com.example.designsystem.DatePickerTextField
import com.example.home.component.Category
import com.example.home.component.CategoryDropdown
import com.example.home.component.CowGroupSlider
import com.example.home.viewmodel.CreateMeetingUIState
import com.example.home.viewmodel.CreateMeetingViewModel
import com.example.model.CreateEvent

@Composable
fun CreateMeetingScreen(
    viewModel: CreateMeetingViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onCreateMeetingSuccess: () -> Unit,
    onEditMeetingSuccess: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
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
        createEvent = uiState.createEvent,
        isEditMode = uiState.isEditMode,
        snackBarHostState = snackBarHostState,
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }

    if (uiState.isCreateMeetingSuccess) {
        onCreateMeetingSuccess()
    }

    if (uiState.isEditMeetingSuccess) {
        onEditMeetingSuccess()
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
    createEvent: CreateEvent,
    isEditMode: Boolean,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
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
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "top_bar_nav_icon_create_meeting",
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
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "모임 이름",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = createEvent.name,
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
            CategoryDropdown(selectedCategory = Category.toLabel(createEvent.category), onCategorySelected = updateCategory)
            Text(
                text = "모임 장소",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = createEvent.location,
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
                date = createEvent.eventDate,
                labelString = "모임 날짜",
                onDateSelected = updateEventDate,
                selectableDateCondition = { utcTimeMillis -> utcTimeMillis <= todayStartOfDayMillis }
            )
            Text(
                text = "정원",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            CowGroupSlider(
                value = createEvent.capacity.toFloat(),
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
                value = createEvent.content,
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
        createEvent = CreateEvent(
            name = "롤",
            category = "게임",
            location = "소환사의 협곡",
            eventDate = "2025-01-19",
            capacity = 80,
            content = "text content",
        ),
    )
}

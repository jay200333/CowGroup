package com.example.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.home.component.Category
import com.example.home.component.CategoryDropdown
import com.example.home.viewmodel.CreateMeetingUIState
import com.example.home.viewmodel.CreateMeetingViewModel
import com.example.model.CreateMeeting

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
        updateCapacity = { capacity -> viewModel.updateCapacity(capacity) },
        updateContent = { content -> viewModel.updateContent(content) },
        createMeeting = uiState.createMeeting,
        isEditMode = uiState.isEditMode,
        createButtonEnabled = uiState.createButtonEnabled,
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
    updateCapacity: (String) -> Unit,
    updateContent: (String) -> Unit,
    createMeeting: CreateMeeting,
    isEditMode: Boolean,
    createButtonEnabled: Boolean,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    if (isEditMode) {
                        Text(
                            text = "모임 편집",
                            style = MaterialTheme.typography.titleLarge
                        )
                    } else {
                        Text(
                            text = "모임 등록",
                            style = MaterialTheme.typography.titleLarge
                        )
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
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "모임명",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = createMeeting.name,
                onValueChange = updateName,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "모임을 대표하는 이름을 적어주세요.") },
                singleLine = true,
            )
            Text(
                text = "모임 소개",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = createMeeting.content,
                onValueChange = updateContent,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                label = { Text(text = "모임을 소개해주세요.") }
            )

            Text(
                text = "카테고리",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            CategoryDropdown(
                selectedCategory = Category.toLabel(createMeeting.category),
                onCategorySelected = updateCategory
            )
            Text(
                text = "모집 인원",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )

            OutlinedTextField(
                value = createMeeting.capacity.toString(),
                onValueChange = updateCapacity,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "1~100") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            )

            if (isEditMode) {
                Button(
                    onClick = onEditButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    enabled = createButtonEnabled
                ) {
                    Text(text = "편집 완료")
                }
            } else {
                Button(
                    onClick = onCreateButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    enabled = createButtonEnabled
                ) {
                    Text(text = "모임 등록하기")
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
        updateCapacity = {},
        updateContent = {},
        isEditMode = false,
        createButtonEnabled = false,
        createMeeting = CreateMeeting(
            name = "롤",
            category = "게임",
            capacity = 80,
            content = "text content",
            file = ""
        ),
    )
}

package com.example.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.theme.CowGroupTheme
import com.example.home.viewmodel.CreatePostUIState
import com.example.home.viewmodel.CreatePostViewModel
import com.example.model.CreatePost

@Composable
fun CreatePostScreen(
    viewModel: CreatePostViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onCreatePostSuccess: () -> Unit,
    onEditPostSuccess: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit
) {
    val uiState: CreatePostUIState by viewModel.uiState.collectAsStateWithLifecycle()

    CreatePostScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onCreateButtonClick = viewModel::createPost,
        onEditButtonClick = {},
        updateTitle = viewModel::updateTitle,
        updateContent = viewModel::updateContent,
        isEditMode = uiState.isEditMode,
        createButtonEnabled = uiState.createButtonEnabled,
        post = uiState.post,
        snackBarHostState = snackBarHostState
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }

    if (uiState.isCreatePostSuccess) {
        onCreatePostSuccess()
    }

    if (uiState.isEditPostSuccess) {
        onEditPostSuccess()
    }
}

@Composable
fun CreatePostScreen(
    onNavigationButtonClick: () -> Unit,
    onCreateButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    updateTitle: (String) -> Unit,
    updateContent: (String) -> Unit,
    isEditMode: Boolean,
    createButtonEnabled: Boolean,
    post: CreatePost,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditMode) "게시글 편집" else "게시글 등록",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "top_bar_nav_icon_create_post",
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                text = "게시글 제목",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondary
            )

            OutlinedTextField(
                value = post.subject,
                onValueChange = updateTitle,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                label = { Text(text = "게시글 제목을 작성해주세요.") },
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "게시글 내용",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondary
            )

            OutlinedTextField(
                value = post.content,
                onValueChange = updateContent,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 230.dp),
                shape = RoundedCornerShape(10.dp),
                label = { Text(text = "내용을 작성해주세요.") },
            )

            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = if (isEditMode) onEditButtonClick else onCreateButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                enabled = createButtonEnabled
            ) {
                Text(
                    text = if (isEditMode) "편집 완료" else "게시글 등록하기",
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePostScreenPreview() {
    CowGroupTheme {
        CreatePostScreen(
            onNavigationButtonClick = {},
            onCreateButtonClick = {},
            onEditButtonClick = {},
            updateTitle = {},
            updateContent = {},
            isEditMode = false,
            createButtonEnabled = false,
            post = CreatePost("제목", "내용")
        )
    }
}
package com.example.mypage.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.DateUtil.todayStartOfDayMillis
import com.example.designsystem.DatePickerTextField
import com.example.model.Profile
import com.example.mypage.R
import com.example.mypage.component.MBTIGridButtons
import com.example.mypage.viewmodel.EditProfileUIState
import com.example.mypage.viewmodel.EditProfileViewModel

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {

    val uiState: EditProfileUIState by viewModel.editProfileUIState.collectAsStateWithLifecycle()

    EditProfileScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        profile = uiState.profile,
        snackBarHostState = snackBarHostState,
        updateName = {},
        updateMBTI = { mbti -> viewModel.updateMBTI(mbti)},
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }
}

@Composable
fun EditProfileScreen(
    onNavigationButtonClick: () -> Unit,
    updateName: (String) -> Unit,
    updateMBTI: (String) -> Unit,
    profile: Profile,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "프로필 편집") },
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "top_bar_nav_edit_profile",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_save_24),
                            contentDescription = "top_bar_icon_edit_profile_save",
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
                text = "닉네임",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = profile.username,
                onValueChange = updateName,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "닉네임을 입력하세요.") },
                placeholder = { Text(text = "닉네임") },
                singleLine = true,
            )
            Text(
                text = "소개",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = profile.introduction,
                onValueChange = updateName,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "소개글을 입력하세요.") },
                placeholder = { Text(text = "소개") },
            )
            Text(
                text = "지역",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = profile.localName,
                onValueChange = updateName,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "지역을 입력하세요.") },
                placeholder = { Text(text = "지역") },
                singleLine = true,
            )
            Text(
                text = "생일",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            DatePickerTextField(
                date = profile.birth,
                labelString = "생년월일",
                onDateSelected = updateName,
                selectableDateCondition = { utcTimeMillis -> utcTimeMillis <= todayStartOfDayMillis }
            )
            Text(
                text = "MBTI",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            MBTIGridButtons(updateMBTI)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(
        onNavigationButtonClick = {},
        updateName = {},
        updateMBTI = {},
        profile = Profile(
            username = "",
            introduction = "",
            localName = "",
            birth = "",
            mbti = ""
        )
    )
}

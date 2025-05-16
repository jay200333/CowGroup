package com.example.mypage.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.designsystem.component.CowGroupDatePickerTextField
import com.example.model.Profile
import com.example.mypage.R
import com.example.mypage.component.MBTIGridButtons
import com.example.mypage.viewmodel.EditProfileUIState
import com.example.mypage.viewmodel.EditProfileViewModel

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onEditProfileSuccess: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {

    val uiState: EditProfileUIState by viewModel.editProfileUIState.collectAsStateWithLifecycle()

    EditProfileScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onEditButtonClick = viewModel::checkEditCondition,
        profile = uiState.profile,
        snackBarHostState = snackBarHostState,
        updateName = { name -> viewModel.updateName(name) },
        updateInstruction = { instruction -> viewModel.updateInstruction(instruction) },
        updateLocation = { location -> viewModel.updateLocation(location) },
        updateBirth = { birth -> viewModel.updateBirth(birth) },
        updateMBTI = { mbti -> viewModel.updateMBTI(mbti) },
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }

        if (uiState.isEditProfileSuccess) {
            onEditProfileSuccess()
        }
    }
}

@Composable
fun EditProfileScreen(
    onNavigationButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    updateName: (String) -> Unit,
    updateInstruction: (String) -> Unit,
    updateLocation: (String) -> Unit,
    updateBirth: (String) -> Unit,
    updateMBTI: (String) -> Unit,
    profile: Profile,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val maxLength = 100
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
                    IconButton(onClick = onEditButtonClick) {
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
                onValueChange = { text ->
                    if (text.length <= maxLength)
                        updateInstruction(text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                label = { Text(text = "소개글을 입력하세요.(100자 제한)") },
                placeholder = { Text(text = "소개") },
            )
            Text(
                text = "지역",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = profile.localName,
                onValueChange = updateLocation,
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
            CowGroupDatePickerTextField(
                date = profile.birth,
                labelString = "생년월일",
                onDateSelected = updateBirth,
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
        onEditButtonClick = {},
        updateName = {},
        updateMBTI = {},
        updateInstruction = {},
        updateLocation = {},
        updateBirth = {},
        profile = Profile(
            username = "",
            introduction = "",
            localName = "",
            birth = "",
            mbti = ""
        )
    )
}

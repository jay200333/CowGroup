package com.example.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.example.login.component.GenderSelectionGrid
import com.example.login.component.MbtiSelectionGrid
import com.example.login.viewmodel.SignUpExtraInfoViewModel
import com.example.login.viewmodel.SignUpUIState

@Composable
fun SignUpExtraInfoScreen(
    viewModel: SignUpExtraInfoViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onSignUpButtonClick: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    val uiState: SignUpUIState by viewModel.signUpUIState.collectAsStateWithLifecycle()

    SignUpExtraInfoScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onSignUpButtonClick = onSignUpButtonClick,
        snackBarHostState = snackBarHostState,
    )
}

@Composable
fun SignUpExtraInfoScreen(
    onNavigationButtonClick: () -> Unit,
    onSignUpButtonClick: () -> Unit,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "회원가입",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "뒤로가기"
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
                .padding(top = 40.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "더 나은 경험을 위해\n간단한 정보를 입력해주세요.",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "성별",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            GenderSelectionGrid(
                selectedGender = "남자",
                onGenderSelected = {}
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "MBTI",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            MbtiSelectionGrid(
                selectedMbti = "INFP",
                onMbtiSelected = {}
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = onSignUpButtonClick,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                enabled = true,
            ) {
                Text(modifier = Modifier.padding(vertical = 10.dp), text = "회원가입", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color.White)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun SignUpExtraInfoScreenPreview() {
    CowGroupTheme {
        SignUpExtraInfoScreen(
            onNavigationButtonClick = {},
            onSignUpButtonClick = {},
        )
    }
}
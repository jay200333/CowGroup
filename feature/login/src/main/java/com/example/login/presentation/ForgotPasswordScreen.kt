package com.example.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.ValidatingTextField
import com.example.designsystem.theme.CowGroupTheme
import com.example.login.R
import com.example.login.viewmodel.ForgotPasswordUIState
import com.example.login.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onToIssueTempPassword: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {

    val uiState: ForgotPasswordUIState by viewModel.forgotPasswordUIState.collectAsStateWithLifecycle()

    ForgotPasswordScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onIssueTempPasswordButtonClick = viewModel::sendTempPassword,
        updateEmail = { email -> viewModel.updateEmail(email) },
        email = uiState.email,
        isValidEmail = uiState.isValidEmail,
        isLoading = uiState.isLoading,
        emailMessage = uiState.emailMessage,
        snackBarHostState = snackBarHostState,
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }

    LaunchedEffect(uiState.sendTempPasswordSuccess) {
        if (uiState.sendTempPasswordSuccess) {
            onToIssueTempPassword()
        }
    }
}

@Composable
fun ForgotPasswordScreen(
    onNavigationButtonClick: () -> Unit,
    onIssueTempPasswordButtonClick: () -> Unit,
    updateEmail: (String) -> Unit,
    email: String,
    isValidEmail: Boolean,
    isLoading: Boolean,
    emailMessage: String,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "비밀번호 찾기",
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
                .padding(top = 80.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "이메일 주소를 입력하고\n임시 비밀번호를 발급받으세요.",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(50.dp))

            ValidatingTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = updateEmail,
                validateCondition = isValidEmail,
                label = { Text(text = "이메일 주소") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                errorMessage = emailMessage,
                trailingIcon = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (email.isNotEmpty()) {
                            Icon(
                                modifier = Modifier.clickable { updateEmail("") },
                                painter = painterResource(R.drawable.baseline_cancel_24),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "email_clear",
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onIssueTempPasswordButtonClick,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                enabled = isValidEmail && !isLoading,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "임시 비밀번호 발급",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    CowGroupTheme {
        ForgotPasswordScreen(
            onNavigationButtonClick = {},
            updateEmail = {},
            email = "",
            isValidEmail = true,
            isLoading = false,
            emailMessage = "",
            onIssueTempPasswordButtonClick = {},
        )
    }
}

package com.example.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.theme.CowGroupTheme
import com.example.login.R
import com.example.login.component.ValidatingTextField
import com.example.login.viewmodel.SignUpUIState
import com.example.login.viewmodel.SignUpViewModel
import com.example.model.SignUpStep1Info

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNextButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
    onSignUpSuccess: () -> Unit,
) {
    val uiState: SignUpUIState by viewModel.signUpUIState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState.isSignUpSuccess) {
            onSignUpSuccess()
        }
    }

    SignUpScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onNextButtonClick = onNextButtonClick,
        updateNickname = { nickname -> viewModel.updateNickname(nickname) },
        updateEmail = { email -> viewModel.updateEmail(email) },
        updateAuthNumber = { authNumber -> viewModel.updateAuthNumber(authNumber) },
        updatePassword = { password -> viewModel.updatePassword(password) },
        updatePasswordConfirm = { passwordConfirm -> viewModel.updatePasswordConfirm(passwordConfirm) },
        checkUsername = viewModel::checkUsername,
        checkEmail = viewModel::checkEmail,
        checkAuthNumber = viewModel::checkAuthNumber,
        passwordConfirm = uiState.passwordConfirm,
        signUpInfo = uiState.signUpInfo,
        authNumber = uiState.authNumber,
        nextButtonEnabled = uiState.nextButtonEnabled,
        isValidUsername = uiState.isValidUsername,
        isValidEmail = uiState.isValidEmail,
        isValidAuthNumber = uiState.isValidAuthNumber,
        passwordCondition = uiState.passwordCondition,
        passwordConfirmCondition = uiState.passwordConfirmCondition,
        usernameMessage = uiState.usernameMessage,
        emailMessage = uiState.emailMessage,
        authNumberMessage = uiState.authNumberMessage,
        passwordMessage = uiState.passwordMessage,
        passwordConfirmMessage = uiState.passwordConfirmMessage,
    )
}

@Composable
fun SignUpScreen(
    onNavigationButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    updateNickname: (String) -> Unit,
    updateEmail: (String) -> Unit,
    updateAuthNumber: (String) -> Unit,
    updatePassword: (String) -> Unit,
    updatePasswordConfirm: (String) -> Unit,
    isValidUsername: Boolean,
    isValidEmail: Boolean,
    isValidAuthNumber: Boolean,
    passwordCondition: Boolean,
    passwordConfirmCondition: Boolean,
    checkUsername: () -> Unit,
    checkEmail: () -> Unit,
    checkAuthNumber: () -> Unit,
    signUpInfo: SignUpStep1Info,
    authNumber: String,
    passwordConfirm: String,
    nextButtonEnabled: Boolean,
    usernameMessage: String,
    emailMessage: String,
    authNumberMessage: String,
    passwordMessage: String,
    passwordConfirmMessage: String,
) {
    var showPassword by remember { mutableStateOf(false) }
    var showPasswordConfirm by remember { mutableStateOf(false) }

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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 80.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ValidatingTextField(
                value = signUpInfo.username,
                onValueChange = { updateNickname(it) },
                validateCondition = isValidUsername,
                label = "닉네임",
                singleLine = true,
                readOnly = isValidUsername,
                errorMessage = usernameMessage,
                trailingIcon = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (signUpInfo.username.isNotEmpty() && isValidUsername.not()) {
                            Icon(
                                modifier = Modifier.clickable { updateNickname("") },
                                painter = painterResource(R.drawable.baseline_cancel_24),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "username_clear",
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { checkUsername() },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                            enabled = isValidUsername.not()
                        ) {
                            Text(text = "중복 확인", color = Color.White)
                        }
                    }
                }
            )

            ValidatingTextField(
                value = signUpInfo.email,
                onValueChange = updateEmail,
                validateCondition = isValidEmail,
                label = "이메일 주소",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                errorMessage = emailMessage,
                readOnly = isValidEmail,
                trailingIcon = {
                    Button(
                        onClick = { checkEmail() },
                        modifier = Modifier.padding(end = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                        enabled = isValidEmail.not()
                    ) {
                        Text(text = "인증하기", color = Color.White)
                    }
                },
            )

            ValidatingTextField(
                value = authNumber,
                onValueChange = updateAuthNumber,
                validateCondition = isValidAuthNumber,
                label = "인증번호 입력",
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                errorMessage = authNumberMessage,
                trailingIcon = {
                    Button(
                        onClick = { checkAuthNumber() },
                        modifier = Modifier.padding(end = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                        enabled = isValidAuthNumber.not()
                    ) {
                        Text(text = "인증 확인", color = Color.White)
                    }
                },
            )

            ValidatingTextField(
                value = signUpInfo.password,
                onValueChange = updatePassword,
                validateCondition = passwordCondition,
                label = "비밀번호 (숫자, 특수문자 포함 8~20자)",
                errorMessage = passwordMessage,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = showPassword.not() }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_visibility_24),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "hide_password",
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPassword = showPassword.not() },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_visibility_off_24),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "show_password",
                            )
                        }
                    }
                },
            )

            ValidatingTextField(
                value = passwordConfirm,
                onValueChange = updatePasswordConfirm,
                validateCondition = passwordConfirmCondition,
                label = "비밀번호 확인",
                errorMessage = passwordConfirmMessage,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPasswordConfirm) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                trailingIcon = {
                    if (showPasswordConfirm) {
                        IconButton(onClick = { showPasswordConfirm = !showPasswordConfirm }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_visibility_24),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "hide_password",
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPasswordConfirm = !showPasswordConfirm },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "show_password",
                            )
                        }
                    }
                },
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onNextButtonClick,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                enabled = nextButtonEnabled,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "다음",
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
fun SignUpScreenPreview() {
    CowGroupTheme {
        SignUpScreen(
            onNavigationButtonClick = {},
            onNextButtonClick = {},
            updateNickname = {},
            updateEmail = {},
            updateAuthNumber = {},
            updatePassword = {},
            updatePasswordConfirm = {},
            signUpInfo = SignUpStep1Info("안드로이드", "", ""),
            authNumber = "",
            nextButtonEnabled = true,
            checkUsername = {},
            checkEmail = {},
            checkAuthNumber = {},
            isValidUsername = false,
            isValidEmail = false,
            isValidAuthNumber = false,
            passwordCondition = false,
            passwordConfirmCondition = false,
            passwordConfirm = "",
            usernameMessage = "",
            emailMessage = "",
            authNumberMessage = "",
            passwordMessage = "",
            passwordConfirmMessage = "",
        )
    }
}

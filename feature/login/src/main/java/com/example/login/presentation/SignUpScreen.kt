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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.example.model.Gender
import com.example.model.SignUpInfo

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNextButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
    onSignUpSuccess: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    val uiState: SignUpUIState by viewModel.signUpUIState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
        if (uiState.isSignUpSuccess) {
            onSignUpSuccess()
        }
    }

    SignUpScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        onNextButtonClick = onNextButtonClick,
        updateNickname = { nickname -> viewModel.updateNickname(nickname) },
        updateEmail = { email -> viewModel.updateEmail(email) },
        updatePassword = { password -> viewModel.updatePassword(password) },
        updatePasswordConfirm = { passwordConfirm -> viewModel.updatePasswordConfirm(passwordConfirm) },
        updateGender = { gender -> viewModel.updateGender(gender) },
        validateEmail = { email -> viewModel.validateEmail(email) },
        validatePassword = viewModel::validatePassword,
        validatePasswordConfirm = viewModel::validatePasswordConfirm,
        checkUsername = viewModel::checkUsername,
        checkEmail = viewModel::checkEmail,
        passwordConfirm = uiState.passwordConfirm,
        signUpInfo = uiState.signUpInfo,
        signUpButtonEnabled = uiState.signUpButtonEnabled,
        validateUsernameButtonEnabled = uiState.validateUsernameButtonEnabled,
        validateEmailButtonEnabled = uiState.validateEmailButtonEnabled,
        isValidUsername = uiState.isValidUsername,
        isValidEmail = uiState.isValidEmail,
        snackBarHostState = snackBarHostState,
    )
}

@Composable
fun SignUpScreen(
    onNavigationButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    updateNickname: (String) -> Unit,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    updatePasswordConfirm: (String) -> Unit,
    updateGender: (Gender) -> Unit,
    validateEmail: (String) -> Boolean,
    validatePassword: (String) -> Boolean,
    validatePasswordConfirm: (String, String) -> Boolean,
    validateUsernameButtonEnabled: Boolean,
    validateEmailButtonEnabled: Boolean,
    isValidUsername: Boolean,
    isValidEmail: Boolean,
    checkUsername: () -> Unit,
    checkEmail: () -> Unit,
    passwordConfirm: String,
    signUpInfo: SignUpInfo,
    signUpButtonEnabled: Boolean,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    var showUsernameMessage by remember { mutableStateOf(false) }
    var showEmailMessage by remember { mutableStateOf(false) }
    var showAuthNumberMessage by remember { mutableStateOf(false) }
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
        snackbarHost = { SnackbarHost(snackBarHostState) }
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
                modifier = Modifier.fillMaxWidth(),
                value = signUpInfo.username,
                onValueChange = {
                    updateNickname(it)
                    showUsernameMessage = false
                },
                validateCondition = isValidUsername,
                showMessage = showUsernameMessage,
                label = "닉네임",
                singleLine = true,
                errorMessage = "이미 사용하고 있는 닉네임입니다.", /* api로 부터 받은 에러 메시지 전달하기 */
                successMessage = "사용할 수 있는 닉네임입니다.",
                trailingIcon = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (signUpInfo.username.isNotEmpty()) {
                            Icon(
                                modifier = Modifier.clickable {
                                    updateNickname("")
                                    showUsernameMessage = false
                                },
                                painter = painterResource(R.drawable.baseline_cancel_24),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "username_clear",
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                checkUsername()
                                showUsernameMessage = true
                            },
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
                modifier = Modifier.fillMaxWidth(),
                value = signUpInfo.email,
                onValueChange = updateEmail,
                validateCondition = isValidEmail,
                label = "이메일 주소",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                errorMessage = "이메일 형식에 맞게 입력해 주세요.", /* api로 부터 받은 에러 메시지 전달하기 */
                successMessage = "인증 요청 이메일이 전송되었습니다.",
                showMessage = false,
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
                modifier = Modifier.fillMaxWidth(),
                value = signUpInfo.email,
                onValueChange = updateEmail,
                validateCondition = isValidEmail.not(),
                label = "인증번호 입력",
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                errorMessage = "인증번호가 올바르지 않습니다.", /* api로 부터 받은 에러 메시지 전달하기 */
                successMessage = "이메일 인증이 완료되었습니다.",
                showMessage = false,
                trailingIcon = {
                    Button(
                        onClick = { checkEmail() },
                        modifier = Modifier.padding(end = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                        enabled = isValidEmail
                    ) {
                        Text(text = "인증 확인", color = Color.White)
                    }
                },
            )

            ValidatingTextField(
                value = signUpInfo.password,
                onValueChange = updatePassword,
                validateCondition = validatePassword(signUpInfo.password),
                modifier = Modifier.fillMaxWidth(),
                label = "비밀번호 (숫자, 특수문자 포함 8~20자)",
                errorMessage = "비밀번호는 영문, 숫자, 특수문자를 포함한 8~16자리여야 합니다.",
                successMessage = "",
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                showMessage = true,
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
                                contentDescription = "hide_password",
                            )
                        }
                    }
                },
            )

            ValidatingTextField(
                value = passwordConfirm,
                onValueChange = updatePasswordConfirm,
                validateCondition = validatePasswordConfirm(
                    signUpInfo.password,
                    passwordConfirm,
                ),
                modifier = Modifier.fillMaxWidth(),
                label = "비밀번호 확인",
                errorMessage = "비밀번호가 일치하지 않습니다.",
                successMessage = "비밀번호가 일치합니다.",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPasswordConfirm) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                showMessage = true,
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
                            onClick = { showPasswordConfirm = showPasswordConfirm.not() },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "hide_password",
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
                enabled = true,//signUpButtonEnabled,
            ) {
                Text(modifier = Modifier.padding(vertical = 10.dp), text = "다음", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color.White)
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
            updatePassword = {},
            updatePasswordConfirm = {},
            updateGender = {},
            signUpInfo = SignUpInfo("안드로이드", Gender.MALE, "", ""),
            validateUsernameButtonEnabled = true,
            validateEmailButtonEnabled = true,
            signUpButtonEnabled = true,
            validateEmail = { true },
            validatePassword = { true },
            validatePasswordConfirm = { _, _ -> true },
            checkUsername = {},
            checkEmail = {},
            isValidUsername = true,
            isValidEmail = false,
            passwordConfirm = "",
        )
    }
}

package com.example.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.login.R
import com.example.login.component.ValidatingTextField
import com.example.login.viewmodel.SignUpUIState
import com.example.login.viewmodel.SignUpViewModel
import com.example.model.Gender
import com.example.model.SignUpInfo

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onLoginTextClick: () -> Unit,
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
        onLoginTextClick = onLoginTextClick,
        onSignUpButtonClick = viewModel::signUp,
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
        snackBarHostState = snackBarHostState,
    )
}

@Composable
fun SignUpScreen(
    onLoginTextClick: () -> Unit,
    onSignUpButtonClick: () -> Unit,
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
    checkUsername: () -> Unit,
    checkEmail: () -> Unit,
    passwordConfirm: String,
    signUpInfo: SignUpInfo,
    signUpButtonEnabled: Boolean,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    var showPassword by remember { mutableStateOf(false) }
    var showPasswordConfirm by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "회원가입",
                modifier = Modifier.padding(vertical = 64.dp),
                style = MaterialTheme.typography.displaySmall,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = signUpInfo.username,
                    onValueChange = updateNickname,
                    label = { Text(text = "닉네임") },
                    placeholder = { Text(text = "닉네임을 입력하세요.") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "icon_person")
                    },
                    singleLine = true,
                )

                Button(
                    modifier = Modifier.wrapContentWidth(),
                    onClick = { checkUsername() },
                    enabled = validateUsernameButtonEnabled,
                ) {
                    Text(text = "중복 확인", style = MaterialTheme.typography.labelSmall)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ValidatingTextField(
                    value = signUpInfo.email,
                    onValueChange = updateEmail,
                    validateCondition = validateEmail(signUpInfo.email),
                    modifier = Modifier.weight(1f),
                    label = "이메일 주소",
                    placeholder = "이메일을 입력하세요.",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "icon_person",
                        )
                    },
                    errorMessage = "이메일 형식에 맞게 입력해 주세요.",
                )
                Button(
                    modifier = Modifier.wrapContentWidth(),
                    onClick = { checkEmail() },
                    enabled = validateEmailButtonEnabled,
                ) {
                    Text(text = "중복 확인", style = MaterialTheme.typography.labelSmall)
                }
            }

            ValidatingTextField(
                value = signUpInfo.password,
                onValueChange = updatePassword,
                validateCondition = validatePassword(signUpInfo.password),
                modifier = Modifier.fillMaxWidth(),
                label = "비밀번호",
                placeholder = "비밀번호를 입력하세요.",
                errorMessage = "비밀번호는 영문, 숫자, 특수문자를 포함한 8~16자리여야 합니다.",
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "PasswordIcon",
                    )
                },
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = showPassword.not() }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_visibility_24),
                                contentDescription = "hide_password",
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPassword = showPassword.not() },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_visibility_off_24),
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
                placeholder = "비밀번호를 입력하세요.",
                errorMessage = "비밀번호가 일치하지 않습니다.",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPasswordConfirm) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "PW2Icon",
                    )
                },
                trailingIcon = {
                    if (showPasswordConfirm) {
                        IconButton(onClick = { showPasswordConfirm = !showPasswordConfirm }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_visibility_24),
                                contentDescription = "hide_password",
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPasswordConfirm = showPasswordConfirm.not() },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                                contentDescription = "hide_password",
                            )
                        }
                    }
                },
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = "성별",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp, end = 32.dp),
                )
                Gender.entries.forEach { genderOption ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp),
                    ) {
                        RadioButton(
                            selected = signUpInfo.gender == genderOption,
                            onClick = { updateGender(genderOption) },
                        )
                        Text(text = genderOption.label)
                    }
                }
            }

            Button(
                onClick = onSignUpButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp),
                enabled = signUpButtonEnabled,
            ) {
                Text(text = "회원 가입")
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "계정이 있으신가요?")
                Text(
                    text = "로그인",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { onLoginTextClick() },
                    color = Color.Blue,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        onLoginTextClick = {},
        onSignUpButtonClick = {},
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
        passwordConfirm = "",
    )
}

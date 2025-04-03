package com.example.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.login.R
import com.example.login.viewmodel.LoginUIState
import com.example.login.viewmodel.LoginViewModel
import com.example.model.LoginInfo

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onSignUpButtonClick: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    val uiState: LoginUIState by viewModel.loginUIState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
        if (uiState.isLoginSuccess) {
            onLoginSuccess()
        }
    }
    LoginScreen(
        onSignUpButtonClick = onSignUpButtonClick,
        onLoginButtonClick = viewModel::login,
        updateEmail = { email -> viewModel.updateEmail(email) },
        updatePassword = { password -> viewModel.updatePassword(password) },
        loginInfo = uiState.loginInfo,
        loginButtonEnabled = uiState.loginButtonEnabled,
        snackBarHostState = snackBarHostState,
    )
}

@Composable
fun LoginScreen(
    onSignUpButtonClick: () -> Unit,
    onLoginButtonClick: () -> Unit,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    loginInfo: LoginInfo,
    loginButtonEnabled: Boolean,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    var showPassword by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cow Group",
                modifier = Modifier.padding(top = 150.dp),
                style = MaterialTheme.typography.displayMedium,
                fontFamily = FontFamily(Font(R.font.kcchyerim, FontWeight.Normal)),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                value = loginInfo.email,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = updateEmail,
                placeholder = { Text(text = "이메일") },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF221E1F)),
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    if (loginInfo.email.isEmpty().not()) {
                        Icon(
                            modifier = Modifier.clickable { updateEmail("") },
                            painter = painterResource(R.drawable.baseline_cancel_24),
                            tint = Color(0xFFB3B3B3),
                            contentDescription = "email_clear",
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = loginInfo.password,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = updatePassword,
                placeholder = { Text(text = "비밀번호") },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF221E1F)),
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = showPassword.not() }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_visibility_24),
                                contentDescription = "hide_password",
                                tint = Color(0xFFB3B3B3)
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPassword = showPassword.not() },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_visibility_off_24),
                                contentDescription = "hide_password",
                                tint = Color(0xFFB3B3B3)
                            )
                        }
                    }
                },
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onLoginButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF221E1F)),
                enabled = loginButtonEnabled,
            ) {
                Text(text = "로그인", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "비밀번호 찾기",
                    modifier = Modifier
                        .clickable { onSignUpButtonClick() }
                        .padding(horizontal = 30.dp),
                    fontSize = 12.sp,
                    color = Color(0xFF3D3D3D),
                )

                VerticalDivider(color = Color.Gray, thickness = 1.dp)

                Text(
                    text = "회원가입",
                    modifier = Modifier
                        .clickable { onSignUpButtonClick() }
                        .padding(horizontal = 30.dp),
                    fontSize = 12.sp,
                    color = Color(0xFF3D3D3D),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onSignUpButtonClick = {},
        onLoginButtonClick = {},
        updateEmail = {},
        updatePassword = {},
        loginInfo = LoginInfo(email = "", password = ""),
        loginButtonEnabled = true,
    )
}

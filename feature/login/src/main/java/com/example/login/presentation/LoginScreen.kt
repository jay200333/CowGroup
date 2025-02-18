package com.example.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.login.viewmodel.LoginUIState
import com.example.login.viewmodel.LoginViewModel
import com.example.model.LoginInfo

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onSignUpButtonClick: () -> Unit,
) {
    val uiState: LoginUIState by viewModel.loginUIState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
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
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Cow Group",
            modifier = Modifier.padding(top = 120.dp),
            style = MaterialTheme.typography.displayLarge,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(64.dp))

        OutlinedTextField(
            value = loginInfo.email,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onValueChange = updateEmail,
            label = { Text(text = "이메일") },
            placeholder = { Text(text = "이메일을 입력하세요.") },
        )

        OutlinedTextField(
            value = loginInfo.password,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onValueChange = updatePassword,
            label = { Text(text = "비밀번호") },
            placeholder = { Text(text = "비밀번호를 입력하세요.") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
        )

        Button(
            onClick = onLoginButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 16.dp),
            enabled = loginButtonEnabled,
        ) {
            Text(text = "로그인")
        }

        Text(
            text = "회원가입",
            modifier = Modifier.clickable { onSignUpButtonClick() },
            color = Color.Gray,
            textDecoration = TextDecoration.Underline,
        )

        HorizontalDivider(color = Color.Gray, thickness = 1.dp)

        Text(text = "SNS 로그인")

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text(text = "구글 로그인 버튼")
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

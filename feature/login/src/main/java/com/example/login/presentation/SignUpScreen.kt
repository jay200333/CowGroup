package com.example.login.presentation

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onLoginTextClick: () -> Unit,
) {
    val uiState: SignUpUIState by viewModel.signUpUIState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "회원가입",
            modifier = Modifier.padding(vertical = 64.dp),
            style = MaterialTheme.typography.displaySmall,
        )

        OutlinedTextField(
            value = uiState.signUpInfo.nickname,
            onValueChange = { viewModel.updateNickname(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "닉네임") },
            placeholder = { Text(text = "닉네임을 입력하세요.") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "icon_person")
            },
            singleLine = true,
        )

        ValidatingTextField(
            value = uiState.signUpInfo.email,
            onValueChange = { viewModel.updateEmail(it) },
            validateCondition = uiState.signUpInfo.email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(
                uiState.signUpInfo.email,
            ).matches(),
            modifier = Modifier.fillMaxWidth(),
            label = "이메일 주소",
            placeholder = "이메일을 입력하세요.",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Email, contentDescription = "icon_person")
            },
            errorMessage = "이메일 형식에 맞게 입력해 주세요.",
        )

        ValidatingTextField(
            value = uiState.signUpInfo.password,
            onValueChange = { viewModel.updatePassword(it) },
            validateCondition = uiState.signUpInfo.password.isEmpty() || uiState.signUpInfo.password.matches(
                viewModel.getPasswordPattern(),
            ),
            modifier = Modifier.fillMaxWidth(),
            label = "비밀번호",
            placeholder = "비밀번호를 입력하세요.",
            errorMessage = "비밀번호는 영문, 숫자, 특수문자를 포함한 8~16자리여야 합니다.",
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (uiState.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "PasswordIcon",
                )
            },
            trailingIcon = {
                if (uiState.showPassword) {
                    IconButton(onClick = { viewModel.updateShowPassword(true) }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_visibility_24),
                            contentDescription = "hide_password",
                        )
                    }
                } else {
                    IconButton(
                        onClick = { viewModel.updateShowPassword(false) },
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
            value = uiState.passwordConfirm,
            onValueChange = { viewModel.updatePasswordConfirm(it) },
            validateCondition = uiState.signUpInfo.password == uiState.passwordConfirm,
            modifier = Modifier.fillMaxWidth(),
            label = "비밀번호 확인",
            placeholder = "비밀번호를 입력하세요.",
            errorMessage = "비밀번호가 일치하지 않습니다.",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (uiState.showPasswordConfirm) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "PW2Icon",
                )
            },
            trailingIcon = {
                if (uiState.showPasswordConfirm) {
                    IconButton(onClick = { viewModel.updateShowPasswordConfirm(true) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_visibility_24),
                            contentDescription = "hide_password",
                        )
                    }
                } else {
                    IconButton(
                        onClick = { viewModel.updateShowPasswordConfirm(false) },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                            contentDescription = "hide_password",
                        )
                    }
                }
            },
        )

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 8.dp),
            enabled = uiState.signUpButtonEnabled,
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

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        onLoginTextClick = {},
    )
}

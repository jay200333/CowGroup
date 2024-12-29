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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.login.R
import com.example.login.component.ValidatingTextField

@Composable
fun SignUpScreen() {
    var nickname by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var pw1 by rememberSaveable { mutableStateOf("") }
    var pw2 by rememberSaveable { mutableStateOf("") }
    var showPw1 by remember { mutableStateOf(false) }
    var showPw2 by remember { mutableStateOf(false) }
    val pwPattern = Regex("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+\\-=]).{8,16}$")
    val signUpButtonEnabled = signUpCondition(nickname, email, pw1, pw2, pwPattern)

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
            value = nickname,
            onValueChange = { nickname = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = "닉네임") },
            placeholder = { Text(text = "닉네임을 입력하세요.") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "icon_person")
            },
        )

        ValidatingTextField(
            value = email,
            onValueChange = { email = it },
            validateCondition = email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email)
                .matches(),
            modifier = Modifier
                .fillMaxWidth(),
            label = "이메일 주소",
            placeholder = "이메일을 입력하세요.",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Email, contentDescription = "icon_person")
            },
            errorMessage = "이메일 형식에 맞게 입력해 주세요.",
        )

        ValidatingTextField(
            value = pw1,
            onValueChange = { pw1 = it },
            validateCondition = pw1.isEmpty() || pw1.matches(pwPattern),
            modifier = Modifier
                .fillMaxWidth(),
            label = "비밀번호",
            placeholder = "비밀번호를 입력하세요.",
            errorMessage = "비밀번호는 영문, 숫자, 특수문자를 포함한 8~16자리여야 합니다.",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (showPw1) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "PW1Icon",
                )
            },
            trailingIcon = {
                if (showPw1) {
                    IconButton(onClick = { showPw1 = false }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_visibility_24),
                            contentDescription = "hide_password",
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPw1 = true },
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
            value = pw2,
            onValueChange = { pw2 = it },
            validateCondition = pw1 == pw2,
            modifier = Modifier
                .fillMaxWidth(),
            label = "비밀번호 확인",
            placeholder = "비밀번호를 입력하세요.",
            errorMessage = "비밀번호가 일치하지 않습니다.",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (showPw2) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "PW2Icon",
                )
            },
            trailingIcon = {
                if (showPw2) {
                    IconButton(onClick = { showPw2 = false }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_visibility_24),
                            contentDescription = "hide_password",
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPw2 = true },
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
                    .clickable { },
                color = Color.Blue,
            )
        }
    }
}

fun signUpCondition(
    nickname: String,
    email: String,
    pw1: String,
    pw2: String,
    pwPattern: Regex,
): Boolean {
    val emailCondition = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val pw1Condition = pw1.isNotEmpty() && pw1.matches(pwPattern)
    val pw2Condition = pw1 == pw2
    return nickname.isNotEmpty() && emailCondition && pw1Condition && pw2Condition
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}

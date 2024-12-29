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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun LoginScreen(
    onSignUpButtonClick: () -> Unit,
) {
    var idText by remember { mutableStateOf("") }
    var pwText by remember { mutableStateOf("") }
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

        TextField(
            value = idText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onValueChange = { idText = it },
            label = { Text(text = "이메일") },
            placeholder = { Text(text = "이메일을 입력하세요.") },
        )

        TextField(
            value = pwText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onValueChange = { pwText = it },
            label = { Text(text = "비밀번호") },
            placeholder = { Text(text = "비밀번호를 입력하세요.") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
        )

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 16.dp),
            enabled = idText.isNotEmpty() && pwText.isNotEmpty(),
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
    )
}

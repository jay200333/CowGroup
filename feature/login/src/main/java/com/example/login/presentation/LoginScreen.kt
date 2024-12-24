package com.example.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "LoginScreen", style = MaterialTheme.typography.displayLarge)
            Button(onClick =  {}) {
                Text(text =  "로그인 버튼")
            }
            Button(onClick =  {}) {
                Text(text =  "회원 가입 버튼")
            }
            Button(onClick =  {}) {
                Text(text =  "구글 로그인 버튼")
            }
        }
    }
}

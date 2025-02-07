package com.example.mycowgroup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun CowGroupApp() {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val onShowSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = message,
            )
        }
    }

    CowGroupNavHost(
        modifier = Modifier.fillMaxSize(),
        navController = rememberNavController(),
        snackBarHostState = snackBarHostState,
        onShowSnackBar = onShowSnackBar,
    )
}

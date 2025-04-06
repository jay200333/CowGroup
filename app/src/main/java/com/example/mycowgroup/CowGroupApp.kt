package com.example.mycowgroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.designsystem.theme.CowGroupTheme
import com.example.main.component.MainBottomBar
import com.example.navigation.bottomBarScreens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CowGroupApp() {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val onShowSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            val job = launch { snackBarHostState.showSnackbar(message = message) }
            delay(2000L)
            job.cancel()
        }
    }
    val navController = rememberNavController()

    CowGroupTheme {
        Scaffold(
            bottomBar = {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination
                if (bottomBarScreens.any { it.route == currentRoute?.route }) {
                    MainBottomBar(navController)
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                CowGroupNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                    onShowSnackBar = onShowSnackBar
                )
            }
        }
    }
}

package com.example.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.main.navigation.HomeScreenRoute
import com.example.main.navigation.MapScreenRoute
import com.example.main.navigation.MyPageScreenRoute

@Composable
fun MainScreen() {
    var selectedScreenIndex by remember { mutableIntStateOf(0) }
    val screenList = listOf(HomeScreenRoute, MapScreenRoute, MyPageScreenRoute)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                screenList.forEachIndexed { index, screen ->
                    val selected = selectedScreenIndex == index
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            selectedScreenIndex = index
                        },
                        label = { Text(text = screen.title) },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconId),
                                contentDescription = "navigationBar Icon",
                            )
                        },
                    )
                }
            }
        },
    ) { innerpadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding),
        ) {
            when (selectedScreenIndex) {
                0 -> HomeScreen()
                1 -> MapScreen()
                2 -> MyPageScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

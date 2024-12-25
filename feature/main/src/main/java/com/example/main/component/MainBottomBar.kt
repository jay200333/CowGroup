package com.example.main.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.navigation.HomeScreenRoute
import com.example.navigation.MapScreenRoute
import com.example.navigation.MyPageScreenRoute

@Composable
fun MainBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    val navItems = listOf(
        HomeScreenRoute,
        MapScreenRoute,
        MyPageScreenRoute,
    )

    NavigationBar {
        navItems.forEach { navItem ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.iconId),
                        contentDescription = navItem.title,
                    )
                },
                label = { Text(navItem.title) },
                selected = currentRoute == navItem,
                onClick = {
                    navController.navigate(navItem) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
            )
        }
    }
}

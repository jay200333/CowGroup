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
import com.example.navigation.bottomBarScreens

@Composable
fun MainBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    NavigationBar {
        bottomBarScreens.forEach { navItem ->
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
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

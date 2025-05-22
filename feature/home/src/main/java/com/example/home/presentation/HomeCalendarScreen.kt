package com.example.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.home.component.HomeScreenSearchBar

@Composable
fun HomeCalendarScreen(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    twoClick: (Int) -> Unit
) {
    val tabTitles = listOf("First", "Second")

    Scaffold(
        topBar = { HomeScreenSearchBar({}, {}, {}, emptyList()) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Action */ }) {
                Icon(Icons.Default.Edit, contentDescription = null)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            SecondaryTabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        text = { Text(title) }
                    )
                }
            }
            Text("Screen Two Content")
            Button(onClick = {twoClick(903)}) {
                Text(text = "버튼 클릭")
            }
        }
    }
}
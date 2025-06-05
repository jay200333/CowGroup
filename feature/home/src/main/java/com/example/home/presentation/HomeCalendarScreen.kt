package com.example.home.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.CowGroupDateRangePicker
import com.example.home.R
import com.example.home.component.DateSelector
import com.example.home.component.HomeScreenSearchBar
import com.example.home.viewmodel.HomeCalendarUISTate
import com.example.home.viewmodel.HomeCalendarViewModel
import java.time.LocalDate

@Composable
fun HomeCalendarScreen(
    viewModel: HomeCalendarViewModel = hiltViewModel(),
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    twoClick: (Int) -> Unit
) {
    val uiState: HomeCalendarUISTate by viewModel.uiState.collectAsState()

    HomeCalendarScreen(
        selectedTabIndex = selectedTabIndex,
        onTabSelected = onTabSelected,
        updateDateRange = viewModel::updateDateRange,
        updateSelectedDate = viewModel::updateSelectedDate,
        dateList = uiState.dateList,
        selectedDate = uiState.selectedDate,
        twoClick = twoClick
    )
}

@Composable
fun HomeCalendarScreen(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    updateDateRange: (Pair<Long?, Long?>) -> Unit,
    updateSelectedDate: (LocalDate) -> Unit,
    dateList: List<LocalDate>,
    selectedDate: LocalDate,
    twoClick: (Int) -> Unit
) {
    val tabTitles = listOf("둘러보기", "날짜보기")
    var showCalendar by remember { mutableStateOf(false) }

    if (showCalendar) {
        CowGroupDateRangePicker(
            onDateRangeSelected = updateDateRange,
            onDismiss = { showCalendar = false }
        )
    }

    Scaffold(
        topBar = { HomeScreenSearchBar({}, {}, {}, emptyList()) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Action */ }) {
                Icon(Icons.Default.Edit, contentDescription = null)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SecondaryTabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        text = { Text(title) }
                    )
                }
            }
            DateSelector(
                dateList = dateList,
                selectedDate = selectedDate,
                onDateClick = updateSelectedDate
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(6.dp)
                    ),
                onClick = { showCalendar = true },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_calendar_month_24),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "btn_home_date_select"
                )
                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = "일정 더 보기",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Button(onClick = { twoClick(903) }) {
                Text(text = "버튼 클릭")
            }
        }
    }
}
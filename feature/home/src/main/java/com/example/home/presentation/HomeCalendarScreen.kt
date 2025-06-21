package com.example.home.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.data.model.SearchHistory
import com.example.designsystem.component.CowGroupDateRangePicker
import com.example.home.R
import com.example.home.component.CowGroupSortDropdownMenu
import com.example.home.component.DateSelector
import com.example.home.component.HomeCalendarRegularMeetingItem
import com.example.home.component.HomeScreenSearchBar
import com.example.home.viewmodel.HomeCalendarUISTate
import com.example.home.viewmodel.HomeCalendarViewModel
import com.example.model.RegularEvent
import java.time.LocalDate

@Composable
fun HomeCalendarScreen(
    viewModel: HomeCalendarViewModel = hiltViewModel(),
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onCreateMeetingClick: (Int, Boolean) -> Unit,
) {
    val uiState: HomeCalendarUISTate by viewModel.uiState.collectAsState()
    val recentSearches by viewModel.getRecentSearches().collectAsState(initial = emptyList())

    HomeCalendarScreen(
        selectedTabIndex = selectedTabIndex,
        onTabSelected = onTabSelected,
        onSearchButtonClick = viewModel::insertQuery,
        onDeleteSearchHistoryButtonClick = viewModel::deleteSearchHistory,
        updateDateRange = viewModel::updateDateRange,
        updateSelectedDate = viewModel::updateSelectedDate,
        updateSearchTerm = viewModel::updateSearchTerm,
        onCreateMeetingClick = { onCreateMeetingClick(0, false) },
        dateList = uiState.dateList,
        selectedDate = uiState.selectedDate,
        regularEventList = uiState.regularEventList,
        recentSearches = recentSearches,
        searchTerm = uiState.searchTerm
    )

    LaunchedEffect(Unit) {
        viewModel.getRegularEventList()
    }
}

@Composable
fun HomeCalendarScreen(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onSearchButtonClick: (String) -> Unit,
    onDeleteSearchHistoryButtonClick: (String) -> Unit,
    updateDateRange: (Pair<Long?, Long?>) -> Unit,
    updateSelectedDate: (LocalDate) -> Unit,
    updateSearchTerm: (String) -> Unit,
    onCreateMeetingClick: () -> Unit,
    dateList: List<LocalDate>,
    selectedDate: LocalDate,
    regularEventList: List<RegularEvent>,
    recentSearches: List<SearchHistory>,
    searchTerm: String
) {
    val listState = rememberLazyListState()
    val tabTitles = listOf("둘러보기", "날짜보기")
    var showCalendar by remember { mutableStateOf(false) }

    if (showCalendar) {
        CowGroupDateRangePicker(
            onDateRangeSelected = updateDateRange,
            onDismiss = { showCalendar = false }
        )
    }

    Scaffold(
        topBar = { HomeScreenSearchBar(updateSearchTerm,
            {},
            onSearchButtonClick,
            onDeleteSearchHistoryButtonClick,
            {},
            recentSearches,
            searchTerm) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreateMeetingClick,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        tint = Color.White,
                        contentDescription = "Fab_HomeScreen"
                    )
                },
                text = { Text(text = "모임 등록", color = Color.White, fontSize = 18.sp) }
            )
        },
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
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = "총 ${regularEventList.size}개",
                    style = MaterialTheme.typography.titleSmall
                )
                CowGroupSortDropdownMenu()
            }

            if (regularEventList.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = listState
                ) {
                    items(
                        count = regularEventList.size,
                        key = { index -> regularEventList[index].id }
                    )
                    { index ->
                        val regularEvent = regularEventList[index]
                        HomeCalendarRegularMeetingItem(
                            regularEvent = regularEvent,
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 20.dp),
                            text = "검색 결과가 없어요.",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "다른 키워드로 검색해보세요.",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}
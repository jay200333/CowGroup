package com.example.mypage.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.CalendarRegularMeetingItem
import com.example.designsystem.component.CowGroupDateRangePicker
import com.example.designsystem.component.DateSelector
import com.example.model.SearchRegularEvent
import com.example.mypage.R
import com.example.mypage.viewmodel.MeetingScheduleUIState
import com.example.mypage.viewmodel.MeetingScheduleViewModel
import java.time.LocalDate

@Composable
fun MeetingScheduleScreen(
    viewModel: MeetingScheduleViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
) {
    val uiState: MeetingScheduleUIState by viewModel.uiState.collectAsState()

    MeetingScheduleScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        updateDateRange = viewModel::updateDateRange,
        updateSelectedDate = viewModel::updateSelectedDate,
        onEventClick = { eventId -> onEventClick(eventId) },
        dateList = uiState.dateList,
        selectedDate = uiState.selectedDate,
        regularEventList = uiState.eventList,
    )
}

@Composable
fun MeetingScheduleScreen(
    onNavigationButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    updateDateRange: (Pair<Long?, Long?>) -> Unit,
    updateSelectedDate: (LocalDate) -> Unit,
    dateList: List<LocalDate>,
    selectedDate: LocalDate,
    regularEventList: List<SearchRegularEvent>,
) {
    val listState = rememberLazyListState()
    var showCalendar by remember { mutableStateOf(false) }

    if (showCalendar) {
        CowGroupDateRangePicker(
            onDateRangeSelected = updateDateRange,
            onDismiss = { showCalendar = false }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "참여 일정 캘린더 보기") },
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "icon_top_bar_nav",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
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
            if (regularEventList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = listState
                ) {
                    items(
                        regularEventList.size
                    )
                    { index ->
                        val regularEvent = regularEventList[index]
                        CalendarRegularMeetingItem(
                            regularEvent = regularEvent,
                            onEventClick = onEventClick
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
package com.example.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.data.model.SearchHistory
import com.example.designsystem.component.PagingMeetingItem
import com.example.home.component.CategorySelector
import com.example.home.component.CowGroupSortDropdownMenu
import com.example.home.component.HomeScreenSearchBar
import com.example.home.viewmodel.HomeUIState
import com.example.home.viewmodel.HomeViewModel
import com.example.model.Category
import com.example.model.Event

@Composable
fun HomeExploreScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onLogoutButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    onCreateMeetingClick: (Int, Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {
    val uiState: HomeUIState by viewModel.homeUIState.collectAsState()
    val pagingEvents = viewModel.pagingEvents.collectAsLazyPagingItems()
    val recentSearches by viewModel.getRecentSearches().collectAsState(initial = emptyList())


    HomeExploreScreen(
        selectedTabIndex = selectedTabIndex,
        onTabSelected = onTabSelected,
        onSearchButtonClick = viewModel::insertQuery,
        onDeleteSearchHistoryButtonClick = viewModel::deleteSearchHistory,
        onLogoutButtonClick = viewModel::logout,
        updateCategory = viewModel::updateCategory,
        updateSearchTerm = viewModel::updateSearchTerm,
        onSearchTermChanged = viewModel::onSearchTermChanged,
        onEventClick = { eventId -> onEventClick(eventId) },
        onCreateMeetingClick = { onCreateMeetingClick(0, false) },
        onBookMarkClick = { eventId, isBookmarked ->
            viewModel.updateBookmark(
                eventId,
                isBookmarked
            )
        },
        eventCount = uiState.eventCount,
        eventList = pagingEvents,
        recentSearches = recentSearches,
        searchTerm = uiState.searchTerm,
        selectedCategory = uiState.selectedCategory,
        snackBarHostState = snackBarHostState,
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }

    if (uiState.isLogout) {
        onLogoutButtonClick()
    }

    LaunchedEffect(Unit) {
        pagingEvents.refresh()
        viewModel.getEventCounts()
    }
}

@Composable
fun HomeExploreScreen(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onSearchButtonClick: (String) -> Unit,
    onDeleteSearchHistoryButtonClick: (String) -> Unit,
    onLogoutButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    onCreateMeetingClick: () -> Unit,
    onBookMarkClick: (Int, Boolean) -> Unit,
    updateCategory: (Category?) -> Unit,
    updateSearchTerm: (String) -> Unit,
    onSearchTermChanged: () -> Unit,
    eventCount: Int,
    eventList: LazyPagingItems<Event>,
    recentSearches: List<SearchHistory>,
    searchTerm: String,
    selectedCategory: Category?,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val listState = rememberLazyListState()
    val tabTitles = listOf("둘러보기", "날짜보기")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeScreenSearchBar(
                updateSearchTerm,
                onSearchTermChanged,
                onSearchButtonClick,
                onDeleteSearchHistoryButtonClick,
                onLogoutButtonClick,
                recentSearches,
                searchTerm
            )
        },
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
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SecondaryTabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        text = { Text(title) },
                    )
                }
            }
            CategorySelector(
                selectedCategory = selectedCategory,
                onCategoryClick = updateCategory
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = "총 ${eventCount}개",
                    style = MaterialTheme.typography.titleSmall
                )
                CowGroupSortDropdownMenu()
            }
            if (eventList.itemCount != 0) {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = listState
                ) {
                    items(eventList.itemCount, key = eventList.itemKey { it.id })
                    { index ->
                        val event = eventList[index]
                        if (event != null) {
                            PagingMeetingItem(
                                event = event,
                                onEventClick = { onEventClick(event.id) },
                                onBookMarkClick = {
                                    onBookMarkClick(
                                        event.id,
                                        event.isBookmarked,
                                    )
                                    eventList.refresh()
                                },
                            )
                        }
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
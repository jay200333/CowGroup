package com.example.mypage.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.MyPageInfo
import com.example.model.MyPageUserInfo
import com.example.mypage.R
import com.example.mypage.component.MeetingItem
import com.example.mypage.viewmodel.MyPageUIState
import com.example.mypage.viewmodel.MyPageViewModel

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = hiltViewModel(),
    onEventClick: (Int) -> Unit,
    onEditProfileButtonClick: () -> Unit,
    onSettingButtonClick: () -> Unit,
    onFullMeetingButtonClick: (Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit
) {
    val uiState: MyPageUIState by viewModel.myPageUIState.collectAsStateWithLifecycle()

    MyPageScreen(
        onEventClick = onEventClick,
        onEditProfileButtonClick = onEditProfileButtonClick,
        onSettingButtonClick = onSettingButtonClick,
        onFullMeetingButtonClick = { isBookmarkPage -> onFullMeetingButtonClick(isBookmarkPage) },
        myPageInfo = uiState.myPageInfo,
        onBookMarkClick = { eventId, isBookmarked ->
            viewModel.updateBookMark(
                eventId,
                isBookmarked
            )
        },
        snackBarHostState = snackBarHostState,
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getMyPage()
    }
}

@Composable
fun MyPageScreen(
    onEventClick: (Int) -> Unit,
    onBookMarkClick: (Int, Boolean) -> Unit,
    onEditProfileButtonClick: () -> Unit,
    onSettingButtonClick: () -> Unit,
    onFullMeetingButtonClick: (Boolean) -> Unit,
    myPageInfo: MyPageInfo,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "마이 페이지") },
                actions = {
                    IconButton(onClick = onEditProfileButtonClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "top_bar_icon_edit"
                        )
                    }

                    IconButton(onClick = onSettingButtonClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "top_bar_icon_setting",
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "닉네임",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = myPageInfo.userInfo.name,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Text(
                            text = "성별",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            modifier = Modifier.padding(start = 8.dp),
                            painter = painterResource(
                                if (myPageInfo.userInfo.gender == "MALE") R.drawable.baseline_male_24 else R.drawable.baseline_female_24,
                            ),
                            contentDescription = "gender_icon",
                            tint = if (myPageInfo.userInfo.gender == "MALE") Color(0XFF2E27F9) else Color(
                                0XFFF674D6
                            ),
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "지역",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = myPageInfo.userInfo.location,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "생일",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = myPageInfo.userInfo.birth,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "MBTI",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = myPageInfo.userInfo.mbti,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Text(
                        text = "소개",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = myPageInfo.userInfo.introduction,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "참여 모임",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                        Text(
                            modifier = Modifier.clickable { onFullMeetingButtonClick(false) },
                            text = "전체 보기 >",
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(myPageInfo.eventList.size) { index ->
                            val event = myPageInfo.eventList[index]
                            MeetingItem(event, onBookMarkClick, onEventClick)
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "북마크 모임",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                        Text(
                            modifier = Modifier.clickable { onFullMeetingButtonClick(true) },
                            text = "전체 보기 >",
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(myPageInfo.bookmarkList.size) { index ->
                            val event = myPageInfo.bookmarkList[index]
                            MeetingItem(event, onBookMarkClick, onEventClick)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageScreenPreview() {
    MyPageScreen(
        onEventClick = {},
        onEditProfileButtonClick = {},
        onSettingButtonClick = {},
        onFullMeetingButtonClick = {},
        myPageInfo = MyPageInfo(
            userInfo = MyPageUserInfo(
                name = " 안드로이드",
                gender = "MALE",
                birth = "1997-06-25",
                mbti = "ISFP",
                location = "서울",
                introduction = "안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이드 안드로이"
            ),
            eventList = emptyList(),
            bookmarkList = emptyList()
        ),
        onBookMarkClick = { _, _ -> },
    )
}

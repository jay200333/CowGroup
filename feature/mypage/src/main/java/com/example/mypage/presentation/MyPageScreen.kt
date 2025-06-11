package com.example.mypage.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.CowGroupAsyncImage
import com.example.designsystem.theme.CowGroupTheme
import com.example.model.MyPageInfo
import com.example.model.MyPageUserInfo
import com.example.mypage.R
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
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding).padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CowGroupAsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(64.dp),
                    imgUrl = "",
                    contentDescription = "img_myPage"
                )
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = "홍길동",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "기본 정보 보기",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                    contentDescription = "icon_user_info"
                )
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 10.dp,
                color = MaterialTheme.colorScheme.onTertiary
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "내 모임 활동",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Column(
                    modifier = Modifier.padding(top = 36.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_local_offer_24),
                            contentDescription = "icon_join_meeting"
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "가입한 모임 목록",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "12개",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Icon(
                            painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                            contentDescription = "icon_user_info"
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(com.example.cowgroup.core.designsystem.R.drawable.baseline_bookmark_border_24),
                            contentDescription = "icon_join_meeting"
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "관심 모임 목록",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "2개",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Icon(
                            painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                            contentDescription = "icon_user_info"
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_calendar_month_24),
                            contentDescription = "icon_join_meeting"
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "참여 일정 캘린더 보기",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                            contentDescription = "icon_user_info"
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onTertiary
                )

                Text(
                    text = "내 활동",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Column(
                    modifier = Modifier.padding(top = 36.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "icon_join_meeting"
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "작성한 게시글 목록",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                            contentDescription = "icon_user_info"
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_chat_bubble_outline_24),
                            contentDescription = "icon_join_meeting"
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "작성한 댓글 목록",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                            contentDescription = "icon_user_info"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageScreenPreview() {
    CowGroupTheme {
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
}

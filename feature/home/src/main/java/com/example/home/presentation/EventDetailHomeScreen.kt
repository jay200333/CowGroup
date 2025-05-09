package com.example.home.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.designsystem.theme.CowGroupTheme
import com.example.home.R
import com.example.home.component.RegularMeetingBottomSheetContent
import com.example.home.component.RegularMeetingItem

@Composable
fun EventDetailHomeScreen(
    onMemberButtonClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {},
                icon = {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(R.drawable.baseline_create_24),
                        contentDescription = "Fab_Event_Detail_HomeScreen",
                        tint = Color.White
                    )
                },
                text = {
                    Text(
                        text = "정기모임 등록",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = "https://picsum.photos/200/300",
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = {},
                    label = { Text(text = "카테고리", style = MaterialTheme.typography.bodySmall) },
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(0.dp, MaterialTheme.colorScheme.onTertiary),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.onTertiary,
                        labelColor = MaterialTheme.colorScheme.onPrimary,
                        leadingIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .graphicsLayer { scaleX = -1f }
                                .size(14.dp),
                            painter = painterResource(R.drawable.baseline_local_offer_24),
                            contentDescription = "카테고리"
                        )
                    })

                AssistChip(
                    onClick = onMemberButtonClick,
                    label = { Text(text = "88명", style = MaterialTheme.typography.bodySmall) },
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(0.dp, MaterialTheme.colorScheme.onTertiary),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.onTertiary,
                        labelColor = MaterialTheme.colorScheme.onPrimary,
                        leadingIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = Icons.Default.Person,
                            contentDescription = "인원"
                        )
                    })
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "여의도 한강공원 러닝크루",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "\uD83C\uDFC3\u200D♂\uFE0F 여의도 한강공원 러닝크루\n 주말마다 함께 달리는 러닝크루입니다. 초보자부터 경험자까지 모두 환영!\n" +
                        "\n" +
                        "\uD83C\uDF33 건강 & 운동\n 한강의 자연을 느끼며, 함께 달리며 건강을 챙겨요.\n" +
                        "\n" +
                        "\uD83D\uDC65 친목 & 소통\n 새로운 사람들과 친목을 나누고, 운동 후 대화의 시간도 가져요.\n" +
                        "\n" +
                        "\uD83C\uDFC5 초보자 환영\n 운동에 익숙하지 않더라도 천천히 따라올 수 있는 일정으로 구성됩니다.\n" +
                        "\n" +
                        "\uD83D\uDDD3 주말 모임\n 매주 주말, 여의도 한강공원에서 만나요!\n" +
                        "\n" +
                        "\uD83D\uDCAC 편안한 분위기\n 스트레칭과 간단한 대화로 서로를 더 잘 알 수 있는 시간을 마련합니다.",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(30.dp))
            HorizontalDivider(thickness = 4.dp, color = MaterialTheme.colorScheme.onTertiary)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "정기모임",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .height(400.dp)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(5) {
                    RegularMeetingItem(onItemClick = {
                        showBottomSheet = true
                    })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 30.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                enabled = true,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "가입하기",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                RegularMeetingBottomSheetContent(onAttendButtonClick = {})
                //val selectedMeeting = meetings.find{ it.id == selectedItemId}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailHomeScreenPreview() {
    CowGroupTheme {
        EventDetailHomeScreen(
            onMemberButtonClick = {}
        )
    }
}
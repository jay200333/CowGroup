package com.example.home.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
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
import com.example.model.DetailEvent

@Composable
fun EventDetailHomeScreen(
    detailEvent: DetailEvent,
    onMemberButtonClick: () -> Unit,
    onJoinButtonClick: () -> Unit,
    onJoinRegularMeetingClick: (Int) -> Unit,
    onCreateRegularMeetingButtonClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (detailEvent.isParticipated) {
                ExtendedFloatingActionButton(
                    onClick = onCreateRegularMeetingButtonClick,
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
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.onTertiary),
                model = detailEvent.url,
                contentDescription = "",
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = detailEvent.category,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
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
                    label = {
                        Text(
                            text = "${detailEvent.applicants}/${detailEvent.capacity}명",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
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
                text = detailEvent.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = detailEvent.content,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(30.dp))
            HorizontalDivider(thickness = 4.dp, color = MaterialTheme.colorScheme.onTertiary)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "정기모임",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    modifier = Modifier.clickable {},
                    text = "전체보기",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (detailEvent.regularEvents.isEmpty()) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "예정된 정기 모임이 없습니다.",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .height(400.dp)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(detailEvent.regularEvents.size) { index ->
                        val regularEvent = detailEvent.regularEvents[index]
                        RegularMeetingItem(regularEvent, onItemClick = {
                            showBottomSheet = true
                        }, onJoinRegularEvent = { onJoinRegularMeetingClick(regularEvent.id) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (detailEvent.isParticipated.not()) {
                Button(
                    onClick = onJoinButtonClick,
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
            detailEvent = DetailEvent(
                id = 0,
                name = "test",
                category = "Sports",
                content = "내용",
                capacity = 100,
                applicants = 20,
                isBookmarked = false,
                url = "",
                eventRegistrant = false,
                isParticipated = false,
                regularEvents = emptyList()
            ),
            onMemberButtonClick = {},
            onJoinButtonClick = {},
            onCreateRegularMeetingButtonClick = {},
            onJoinRegularMeetingClick = {}
        )
    }
}
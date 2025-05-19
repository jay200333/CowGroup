package com.example.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.DateUtil
import com.example.designsystem.theme.CowGroupTheme
import com.example.home.R
import com.example.model.RegularEvent

@Composable
fun RegularMeetingBottomSheetContent(
    regularEvent: RegularEvent,
    onAttendButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssistChip(
                onClick = {},
                label = { Text(text = "정기모임", style = MaterialTheme.typography.bodySmall) },
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
                onClick = {},
                label = {
                    Text(
                        text = "${regularEvent.applicants}/${regularEvent.capacity}명",
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
            modifier = Modifier.padding(bottom = 10.dp),
            text = regularEvent.name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.baseline_calendar_month_24),
                contentDescription = "icon_meeting_date",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "일시",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = DateUtil.formatIsoToRegularDate(regularEvent.dateTime),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(com.example.navigation.R.drawable.baseline_map_24),
                contentDescription = "icon_meeting_location",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "위치",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = regularEvent.location,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        if (regularEvent.isRegularRegistrant) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onEditButtonClick() },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = "수정하기",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = "삭제하기",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                onClick = { onAttendButtonClick() },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (regularEvent.isParticipated) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = if (regularEvent.isParticipated) "참석취소" else "참석하기",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetContentPreview() {
    CowGroupTheme {
        RegularMeetingBottomSheetContent(
            regularEvent = RegularEvent(
                id = 0,
                name = "test",
                location = "한국",
                dateTime = "4/25(금) 오후 7:30",
                capacity = 100,
                applicants = 20,
                isRegularRegistrant = false,
                isParticipated = false
            ),
            onAttendButtonClick = {},
            onEditButtonClick = {}
        )
    }
}
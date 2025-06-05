package com.example.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cowgroup.core.designsystem.R
import com.example.designsystem.component.CowGroupAsyncImage
import com.example.model.RegularEvent

@Composable
fun HomeCalendarRegularMeetingItem(regularEvent: RegularEvent) {
    Row(modifier = Modifier.fillMaxWidth().clickable {}, verticalAlignment = Alignment.CenterVertically) {
        CowGroupAsyncImage(
            modifier = Modifier.clip(MaterialTheme.shapes.medium).size(70.dp),
            imgUrl = "",
            contentDescription = "profile_img",
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = regularEvent.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .graphicsLayer { scaleX = -1f }
                        .size(16.dp),
                    painter = painterResource(R.drawable.baseline_local_offer_24),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "icon_paging_item_category"
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "카테고리", // api 모델 변경해야함
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    painter = painterResource(com.example.home.R.drawable.baseline_calendar_month_24),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "icon_paging_item_date"
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = regularEvent.dateTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier
                        .size(16.dp),
                    imageVector = Icons.Default.Person,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "icon_paging_item_applicants"
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = "${regularEvent.applicants}/${regularEvent.capacity}명",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
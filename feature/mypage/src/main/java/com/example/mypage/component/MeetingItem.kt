package com.example.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.MarkButton
import com.example.model.MyPageEvent
import com.example.mypage.R

@Composable
fun MeetingItem(event: MyPageEvent) {
    Card(
        modifier = Modifier.width(250.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = event.eventName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                MarkButton(
                    isMarked = event.isBookMarked,
                    onMarkClick = { },
                    markedIconId = R.drawable.baseline_bookmarks_24,
                    unMarkedIconId = R.drawable.baseline_bookmarks_24,
                )
            }
            Text(
                text = event.eventDate,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = event.author,
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "icon_people")
                Spacer(modifier = Modifier.padding(start = 4.dp))
                Text(
                    text = "${event.applicants} / ${event.capacity}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview
@Composable
fun MeetingItemPreview() {
    MeetingItem(
        event = MyPageEvent(
            eventName = "안드로이드",
            eventDate = "1997-10-26",
            author = "안드로이드",
            applicants = 1,
            capacity = 2,
            isBookMarked = false,
            id = 1
        )
    )
}
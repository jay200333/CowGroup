package com.example.designsystem.component

import android.net.Uri
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cowgroup.core.designsystem.R
import com.example.model.Event

@Composable
fun PagingMeetingItem(event: Event, onBookMarkClick: (Boolean) -> Unit, onEventClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { onEventClick() }, verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            modifier = Modifier.clip(MaterialTheme.shapes.medium).size(70.dp),
            model = Uri.parse("https://picsum.photos/200/300"),
            contentDescription = "profile_img",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = event.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = event.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
                    text = "${event.participants}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Spacer(modifier = Modifier.width(30.dp))
        MarkButton(
            modifier = Modifier.padding(bottom = 30.dp),
            isMarked = event.isBookmarked,
            onMarkClick = { isBookMarked -> onBookMarkClick(isBookMarked) },
            markedIconId = R.drawable.baseline_bookmark_24,
            unMarkedIconId = R.drawable.baseline_bookmark_border_24,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MeetingItemPreview() {
    PagingMeetingItem(
        onBookMarkClick = {},
        onEventClick = {},
        event = Event(
            id = 1,
            name = "test1",
            author = "홍길동",
            content = "test1",
            createdDate = "2024-12-31",
            capacities = 10,
            participants = 100,
            isBookmarked = false,
        ),
    )
}

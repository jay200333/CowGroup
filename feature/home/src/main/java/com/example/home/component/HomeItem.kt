package com.example.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.MarkButton
import com.example.home.R
import com.example.home.viewmodel.HomeViewModel
import com.example.model.Event

@Composable
fun HomeItem(viewModel: HomeViewModel, event: Event, onEventClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .clickable { onEventClick() },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = event.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(text = event.createDate, style = MaterialTheme.typography.labelSmall)
        }
        Text(
            text = event.content,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "이벤트 일시",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(text = event.eventDate, style = MaterialTheme.typography.labelMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            MemberTab(event.capacities, event.participants)
            MarkButton(
                isMarked = event.isBookmarked,
                onMarkClick = { isBookmarked -> viewModel.updateBookmark(event.id, isBookmarked) },
                markedIconId = R.drawable.baseline_bookmarks_24,
                unMarkedIconId = R.drawable.baseline_bookmarks_24,
            )
        }
    }
}

@Composable
fun MemberTab(capacities: Int, participants: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.baseline_how_to_reg_24),
            contentDescription = "icon_member",
        )
        Text(text = "$participants / $capacities", style = MaterialTheme.typography.labelMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeItemPreview() {
    HomeItem(
        viewModel = HomeViewModel(),
        onEventClick = {},
        event = Event(
            id = 1,
            name = "test1",
            content = "test1",
            eventDate = "2025-12-7",
            createDate = "2024-12-31",
            capacities = 10,
            participants = 100,
            isBookmarked = false,
        ),
    )
}

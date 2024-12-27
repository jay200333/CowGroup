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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun HomeItem(itemClick: () -> Unit) {
    var isBookMarked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .clickable { itemClick() },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "이벤트 생성자",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(text = "10분 전", style = MaterialTheme.typography.labelSmall)
        }
        Text(
            text = "이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용 이벤트 내용",
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "이벤트 일시",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(text = "2024-12-27", style = MaterialTheme.typography.labelMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            MemberTab(10, 5)
            MarkButton(
                isMarked = false,
                onMarkClick = { isBookMarked = it },
                markedIconId = R.drawable.baseline_bookmarks_24,
                unMarkedIconId = R.drawable.baseline_bookmarks_24,
            )
        }
    }
}

@Composable
fun MemberTab(totalNumber: Int, currentNumber: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.baseline_how_to_reg_24),
            contentDescription = "icon_member",
        )
        Text(text = "$currentNumber / $totalNumber", style = MaterialTheme.typography.labelMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeItemPreview() {
    HomeItem(
        itemClick = {},
    )
}

package com.example.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common.DateUtil
import com.example.designsystem.component.CowGroupAsyncImage
import com.example.designsystem.theme.CowGroupTheme
import com.example.home.R
import com.example.model.Post

@Composable
fun EventDetailBoardItem(post: Post, onChatClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CowGroupAsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(35.dp),
                imgUrl = "",
                contentDescription = "profile_img"
            )

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = post.userName,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = DateUtil.formatIsoToRegularDate(post.dateTime),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.weight(1f))

            EditDropDownMenu(
                menuItems = listOf(
                    DropDownMenuItem("수정하기") { },
                    DropDownMenuItem("삭제하기") { }
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = post.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = post.content,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primaryContainer
        )

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onPrimary)

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onChatClick() },
                painter = painterResource(R.drawable.baseline_chat_bubble_outline_24),
                contentDescription = "board_item_icon"
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onChatClick() },
                text = "댓글",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primaryContainer
            )
            Text(
                modifier = Modifier.clickable { onChatClick() },
                text = "${post.commentCount}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primaryContainer,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailBoardItemPreview() {
    CowGroupTheme {
        EventDetailBoardItem(
            onChatClick = {},
            post = Post(
                id = 0,
                title = "test1",
                content = "test1",
                dateTime = "",
                userName = "manager",
                commentCount = 5,
                isRegistrant = false
            )
        )
    }
}

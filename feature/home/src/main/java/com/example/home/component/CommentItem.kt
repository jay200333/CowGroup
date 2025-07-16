package com.example.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.CowGroupAsyncImage
import com.example.designsystem.component.DropDownMenuItem
import com.example.designsystem.component.EditDropDownMenu
import com.example.model.Comment

@Composable
fun CommentItem(comment: Comment, onDeleteButtonClick: () -> Unit, onEditButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                contentDescription = "profile_img",
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = comment.username,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = comment.daysAgo,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.weight(1f))
            if (comment.isRegistrant) {
                EditDropDownMenu(
                    menuItems = listOf(
                        DropDownMenuItem("수정하기") { onEditButtonClick() },
                        DropDownMenuItem("삭제하기") { onDeleteButtonClick() }
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = comment.content,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}
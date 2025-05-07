package com.example.home.component

import android.net.Uri
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun CommentItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(35.dp),
                model = Uri.parse("https://picsum.photos/200/300"),
                contentDescription = "profile_img",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "홍길동",
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "1일 전",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.weight(1f))

            EditDropDownMenu()
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "반갑습니다.",
            style = MaterialTheme.typography.titleSmall,
        )
    }
}
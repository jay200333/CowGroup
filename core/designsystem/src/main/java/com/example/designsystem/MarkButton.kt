package com.example.designsystem

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cowgroup.core.designsystem.R

@Composable
fun MarkButton(
    isMarked: Boolean,
    onMarkClick: (Boolean) -> Unit,
    markedIconId: Int,
    unMarkedIconId: Int,
) {
    var marked by remember { mutableStateOf(isMarked) }
    IconButton(
        onClick = {
            marked = !marked
            onMarkClick(marked)
        },
    ) {
        Icon(
            painter = painterResource(if (marked) markedIconId else unMarkedIconId),
            contentDescription = "btn_book_mark",
            tint = if (marked) MaterialTheme.colorScheme.primary else Color.Gray,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MarkButtonPreview() {
    MarkButton(
        isMarked = true,
        onMarkClick = {},
        markedIconId = R.drawable.baseline_bookmarks_24,
        unMarkedIconId = R.drawable.baseline_bookmark_border_24,
    )
}

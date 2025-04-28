package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CowGroupSelectionGrid(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .heightIn(max = 200.dp)
        .clip(RoundedCornerShape(10.dp))
        .border(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp)),
    gridCellCount: Int = 4,
    selectedContent: String,
    onContentSelected: (String) -> Unit,
    selectionList: List<String>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(gridCellCount),
        modifier = modifier,
    ) {
        items(selectionList.size) { index ->
            val item = selectionList[index]
            val isSelected = item == selectedContent
            Box(
                modifier = Modifier
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.White)
                    .border(width = 0.5f.dp, color = MaterialTheme.colorScheme.onPrimary)
                    .clickable { onContentSelected(item) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 15.dp),
                    text = item,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
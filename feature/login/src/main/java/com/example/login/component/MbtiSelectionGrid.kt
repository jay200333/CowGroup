package com.example.login.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.CowGroupTheme

@Composable
fun MbtiSelectionGrid(
    selectedMbti: String,
    onMbtiSelected: (String) -> Unit
) {
    val mbtiList = listOf(
        "ISTJ", "ISFJ", "INFJ", "INTJ",
        "ISTP", "ISFP", "INFP", "INTP",
        "ESTP", "ESFP", "ENFP", "ENTP",
        "ESTJ", "ESFJ", "ENFJ", "ENTJ"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(10.dp)),
    ) {
        items(mbtiList.size) { index ->
            val mbti = mbtiList[index]
            val isSelected = mbti == selectedMbti
            Box(
                modifier = Modifier
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.White)
                    .border(width = 0.5f.dp, color = MaterialTheme.colorScheme.onPrimary)
                    .clickable { onMbtiSelected(mbti) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 15.dp),
                    text = mbti,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MbtiGridPreview() {
    CowGroupTheme {
        MbtiSelectionGrid(
            selectedMbti = "ISTJ",
            onMbtiSelected = {}
        )
    }
}
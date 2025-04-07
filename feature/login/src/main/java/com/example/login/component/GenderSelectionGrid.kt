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
fun GenderSelectionGrid(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    val genderOptions = listOf("남자", "여자")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary),
    ) {
        items(genderOptions.size) { index ->
            val gender = genderOptions[index]
            val isSelected = gender == selectedGender
            Box(
                modifier = Modifier
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.White)
                    .border(width = 0.5f.dp, color = MaterialTheme.colorScheme.onPrimary)
                    .clickable { onGenderSelected(gender) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = gender,
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
fun GenderSelectionGridPreview() {
    CowGroupTheme {
        GenderSelectionGrid(
            selectedGender = "남자",
            onGenderSelected = {}
        )
    }
}
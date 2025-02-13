package com.example.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MBTIGridButtons(updateMBTI: (String) -> Unit) {
    val mbtiList = MBTI.entries.map { it.name }
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(MBTI.entries.size) { index ->
                val isSelected = index == selectedIndex
                Button(
                    onClick = {
                        selectedIndex = index
                        updateMBTI(mbtiList[selectedIndex])
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Color.Blue else Color.Gray
                    ),
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraLarge)
                ) {
                    Text(
                        mbtiList[index],
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

enum class MBTI {
    INTJ, INTP, INFJ, INFP,
    ISTJ, ISTP, ISFJ, ISFP,
    ENTJ, ENTP, ENFJ, ENFP,
    ESTJ, ESTP, ESFJ, ESFP,
}
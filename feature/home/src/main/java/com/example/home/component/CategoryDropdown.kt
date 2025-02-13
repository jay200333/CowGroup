package com.example.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun CategoryDropdown(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = Category.entries.map { it.label } // Enum 값을 문자열 리스트로 변환
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedCategory,
            onValueChange = {},
            readOnly = true, // 사용자가 직접 입력하지 못하도록 설정
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            label = { Text("카테고리를 선택하세요.") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategorySelected(Category.fromLabel(category).toString())
                        expanded = false
                    }
                )
            }
        }
    }
}

enum class Category(val label: String) {
    MUSIC("음악"),
    FASHION("패션"),
    TRAVEL("여행"),
    BEAUTY("뷰티"),
    PICTURE("사진"),
    SPORTS("운동"),
    READING("독서"),
    FOOD("음식");

    companion object {
        private val categoryMap = entries.associateBy { it.label }
        fun fromLabel(label: String): Category? = categoryMap[label]
        fun toLabel(category: String): String {
            return if (category.isEmpty()) "" else
                Category.valueOf(category).label
        }
    }
}
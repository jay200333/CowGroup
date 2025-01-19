package com.example.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale

@Composable
fun DatePickerTextField(
    date: String,
    onDateSelected: (String) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Box(modifier = Modifier.clickable { showDatePicker = true }) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = date,
            onValueChange = {},
            label = {
                Text(text = "모임 날짜")
            },
            enabled = false,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "날짜 선택",
                    )
                }
            },
        )

        if (showDatePicker) {
            CowGroupDatePicker(
                onDateSelected = { onDateSelected(it?.let { convertMillisToDate(it) } ?: "") },
                onDismiss = { showDatePicker = false },
            )
        }
    }
}

@Composable
private fun CowGroupDatePicker(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val todayStartOfDayMillis =
        LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                utcTimeMillis >= todayStartOfDayMillis
        },
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                },
            ) {
                Text(text = "확인")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text(text = "취소")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
fun DatePickerTextFieldPreview() {
    DatePickerTextField(
        date = "2023-10-01",
        onDateSelected = {},
    )
}

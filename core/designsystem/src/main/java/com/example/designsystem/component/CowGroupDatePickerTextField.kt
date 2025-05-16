package com.example.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.DateUtil.convertMillisToDate
import com.example.common.DateUtil.parseDateStringToMillis
import com.example.common.DateUtil.todayStartOfDayMillis

@Composable
fun CowGroupDatePickerTextField(
    date: String,
    labelString: String,
    onDateSelected: (String) -> Unit,
    selectableDateCondition: (Long) -> Boolean
) {
    var showDatePicker by remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { showDatePicker = true },
        shape = RoundedCornerShape(10.dp),
        value = date,
        onValueChange = { /* no op */ },
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = labelString,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        },

        enabled = false,
        colors = TextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.primary,
            disabledIndicatorColor = Color.Transparent,
            disabledContainerColor = Color.White
        ),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onSecondary
        )
    )

    if (showDatePicker) {
        CowGroupDatePicker(
            selectedDateMillis = parseDateStringToMillis(date),
            onDateSelected = { onDateSelected(it?.let { convertMillisToDate(it) } ?: "") },
            onDismiss = { showDatePicker = false },
            selectableDateCondition = selectableDateCondition
        )
    }
}

@Composable
private fun CowGroupDatePicker(
    selectedDateMillis: Long? = todayStartOfDayMillis,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    selectableDateCondition: (Long) -> Boolean
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                selectableDateCondition(utcTimeMillis)
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
        colors = DatePickerDefaults.colors(
            containerColor = Color.White
        )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                todayDateBorderColor = Color.Transparent,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DatePickerTextFieldPreview() {
    CowGroupDatePickerTextField(
        date = "2023-10-01",
        onDateSelected = {},
        labelString = "날짜",
        selectableDateCondition = { true }
    )
}

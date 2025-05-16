package com.example.designsystem.component

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CowGroupTimePickerTextField(
    time: String,
    labelString: String,
    onTimeSelected: (String) -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { showTimePicker = true },
        shape = RoundedCornerShape(10.dp),
        value = time,
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

    if (showTimePicker) {
        CowGroupTimePicker(
            onDismiss = { showTimePicker = false },
            onTimeSelected = { onTimeSelected(it) }
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CowGroupTimePicker(
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color.White,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "정기모임 일시",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                TimeInput(
                    state = timePickerState, colors = TimePickerDefaults.colors(
                        containerColor = Color.White,
                        timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.onPrimary,
                        timeSelectorSelectedContentColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("취소")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            val cal = Calendar.getInstance().apply {
                                set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                set(Calendar.MINUTE, timePickerState.minute)
                            }

                            val sdf = SimpleDateFormat("a h:mm", Locale.KOREA)
                            val formattedTime = sdf.format(cal.time) // 예: 오후 7:30
                            onTimeSelected(formattedTime)
                            onDismiss()
                        }
                    ) {
                        Text("확인")
                    }
                }
            }
        }
    }
}
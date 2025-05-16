package com.example.home.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.DateUtil.todayStartOfDayMillis
import com.example.designsystem.component.CowGroupDatePickerTextField
import com.example.designsystem.component.CowGroupTimePickerTextField
import com.example.designsystem.component.ValidatingTextField
import com.example.designsystem.theme.CowGroupTheme
import com.example.home.R
import com.example.home.viewmodel.CreateRegularMeetingUIState
import com.example.home.viewmodel.CreateRegularMeetingViewModel
import com.example.model.CreateRegularMeeting

@Composable
fun CreateRegularMeetingScreen(
    viewModel: CreateRegularMeetingViewModel = hiltViewModel(),
    onNavigationButtonClick: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
) {

    val uiState: CreateRegularMeetingUIState by viewModel.createRegularMeetingUIState.collectAsStateWithLifecycle()

    CreateRegularMeetingScreen(
        onNavigationButtonClick = onNavigationButtonClick,
        isEditMode = uiState.isEditMode,
        regularMeeting = uiState.regularMeeting,
        regularMeetingDate = uiState.regularMeetingDate,
        regularMeetingTime = uiState.regularMeetingTime,
        onDateChange = viewModel::setRegularMeetingDate,
        onTimeChange = viewModel::setRegularMeetingTime,
        updateName = viewModel::updateName,
        updateLocation = viewModel::updateLocation,
        updateCapacity = viewModel::updateCapacity,
        isValidCapacity = uiState.isValidCapacity,
        capacityMessage = uiState.capacityMessage,
        createButtonEnabled = uiState.createButtonEnabled,
        snackBarHostState = snackBarHostState
    )

    LaunchedEffect(uiState) {
        if (uiState.message.isNotEmpty()) {
            onShowSnackBar(uiState.message)
            viewModel.setMessageClear()
        }
    }
}

@Composable
fun CreateRegularMeetingScreen(
    onNavigationButtonClick: () -> Unit,
    isEditMode: Boolean,
    regularMeeting: CreateRegularMeeting,
    regularMeetingDate: String,
    regularMeetingTime: String,
    onDateChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    updateName: (String) -> Unit,
    updateLocation: (String) -> Unit,
    updateCapacity: (String) -> Unit,
    isValidCapacity: Boolean,
    capacityMessage: String,
    createButtonEnabled: Boolean,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    if (isEditMode) {
                        Text(
                            text = "정기모임 편집",
                            style = MaterialTheme.typography.titleLarge
                        )
                    } else {
                        Text(
                            text = "정기모임 등록",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationButtonClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "top_bar_nav_icon_create_meeting",
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp, top = 30.dp, bottom = 8.dp)
        ) {
            Text(
                text = "모임명",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(10.dp)
                    ),
                value = regularMeeting.name,
                onValueChange = updateName,
                shape = RoundedCornerShape(10.dp),
                label = {
                    Text(
                        text = "정기모임의 이름을 적어주세요.",
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color.White
                ),
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row {
                Row(
                    modifier = Modifier.padding(top = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(R.drawable.baseline_calendar_month_24),
                        contentDescription = "icon_regular_create_meeting_date"
                    )

                    Text(
                        modifier = Modifier.padding(start = 6.dp),
                        text = "일시",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }

                Spacer(modifier = Modifier.width(95.dp))

                Column {
                    CowGroupDatePickerTextField(
                        date = regularMeetingDate,
                        labelString = "일정을 선택하세요.",
                        onDateSelected = { onDateChange(it) },
                        selectableDateCondition = { utcTimeMillis -> utcTimeMillis >= todayStartOfDayMillis })

                    Spacer(modifier = Modifier.height(16.dp))

                    CowGroupTimePickerTextField(
                        time = regularMeetingTime,
                        labelString = "시간을 선택하세요.",
                        onTimeSelected = { onTimeChange(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(R.drawable.baseline_map_24),
                    contentDescription = "icon_regular_create_meeting_location"
                )

                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = "위치",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary
                )

                Spacer(modifier = Modifier.width(95.dp))

                TextField(
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    value = regularMeeting.location,
                    onValueChange = updateLocation,
                    shape = RoundedCornerShape(10.dp),
                    label = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            text = "장소를 입력하세요.",
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.White
                    ),
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.Person,
                    contentDescription = "icon_create_meeting_capacity"
                )
                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = "모집 인원",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.width(60.dp))

                ValidatingTextField(
                    modifier = Modifier.weight(1f),
                    value = if (regularMeeting.capacity == 0) "" else regularMeeting.capacity.toString(),
                    onValueChange = updateCapacity,
                    validateCondition = isValidCapacity,
                    label = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "1~100",
                            )
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    errorMessage = capacityMessage,
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "명",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.height(130.dp))

            if (isEditMode) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    enabled = createButtonEnabled
                ) {
                    Text(
                        text = "편집 완료",
                        modifier = Modifier.padding(vertical = 10.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            } else {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    enabled = createButtonEnabled
                ) {
                    Text(
                        text = "정기모임 등록하기",
                        modifier = Modifier.padding(vertical = 10.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CreateRegularMeetingScreenPreview() {
    CowGroupTheme {
        CreateRegularMeetingScreen(
            onNavigationButtonClick = {},
            isEditMode = false,
            regularMeeting = CreateRegularMeeting(
                name = "",
                location = "",
                capacity = 80,
                dateTime = ""
            ),
            regularMeetingDate = "2023-10-01",
            regularMeetingTime = "12:00",
            onDateChange = {},
            onTimeChange = {},
            updateName = {},
            updateLocation = {},
            updateCapacity = {},
            isValidCapacity = false,
            capacityMessage = "",
            createButtonEnabled = false
        )
    }
}
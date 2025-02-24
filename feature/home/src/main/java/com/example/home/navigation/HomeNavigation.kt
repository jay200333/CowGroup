package com.example.home.navigation

import android.os.Bundle
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.example.home.presentation.CreateMeetingScreen
import com.example.home.presentation.EventDetailScreen
import com.example.home.presentation.HomeScreen
import com.example.home.presentation.MemberScreen
import com.example.model.CreateEvent
import com.example.navigation.CreateMeetingRoute
import com.example.navigation.EventDetailRoute
import com.example.navigation.HomeScreenRoute
import com.example.navigation.MemberRoute
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

fun NavController.navigateHome() {
    navigate(HomeScreenRoute) {
    }
}

fun NavController.navigateCreateMeeting(eventId: Int, isEditMode: Boolean, event: CreateEvent) {
    navigate(CreateMeetingRoute(eventId, isEditMode, event))
}

fun NavController.navigateEventDetail(eventId: Int) {
    navigate(EventDetailRoute(eventId))
}

fun NavController.navigateMember() {
    navigate(MemberRoute)
}

fun NavGraphBuilder.homeNavGraph(
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
    onLogoutButtonClick: () -> Unit,
    onEventClick: (Int) -> Unit,
    onCreateMeetingClick: (Int, Boolean, CreateEvent) -> Unit,
    onMemberButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit,
    onCreateMeetingSuccess: () -> Unit,
    onEditMeetingSuccess: () -> Unit,
) {
    composable<HomeScreenRoute> {
        HomeScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onLogoutButtonClick = onLogoutButtonClick,
            onEventClick = { eventId -> onEventClick(eventId) },
            onCreateMeetingClick = { eventId, isEditMode, event -> onCreateMeetingClick(eventId, isEditMode, event) },
        )
    }
    composable<CreateMeetingRoute>(
        typeMap = mapOf(typeOf<CreateEvent>() to CreateEventType)
    ) {
        CreateMeetingScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onNavigationButtonClick = onNavigationButtonClick,
            onCreateMeetingSuccess = onCreateMeetingSuccess,
            onEditMeetingSuccess = onEditMeetingSuccess,
        )
    }
    composable<EventDetailRoute> {
        EventDetailScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onMemberButtonClick = onMemberButtonClick,
            onNavigationButtonClick = onNavigationButtonClick,
            onEditButtonClick = { eventId, isEditMode, event -> onCreateMeetingClick(eventId, isEditMode, event) },
        )
    }
    composable<MemberRoute> {
        MemberScreen(
            onNavigationButtonClick = onNavigationButtonClick,
        )
    }
}

val CreateEventType = object : NavType<CreateEvent>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): CreateEvent? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): CreateEvent {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: CreateEvent) {
        bundle.putString(key, Json.encodeToString(CreateEvent.serializer(), value))
    }

    override fun serializeAsValue(value: CreateEvent): String {
        return Json.encodeToString(CreateEvent.serializer(), value)
    }
}

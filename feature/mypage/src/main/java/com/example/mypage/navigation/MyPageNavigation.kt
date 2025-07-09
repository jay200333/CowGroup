package com.example.mypage.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mypage.presentation.BookmarkMeetingScreen
import com.example.mypage.presentation.EditProfileScreen
import com.example.mypage.presentation.FullMeetingScreen
import com.example.mypage.presentation.JoinMeetingScreen
import com.example.mypage.presentation.MeetingScheduleScreen
import com.example.mypage.presentation.MyCommentsScreen
import com.example.mypage.presentation.MyPageScreen
import com.example.mypage.presentation.MyPostsScreen
import com.example.mypage.presentation.UserInfoScreen
import com.example.navigation.EditProfileRoute
import com.example.navigation.FullBookmarkMeetingRoute
import com.example.navigation.FullJoinMeetingRoute
import com.example.navigation.FullMeetingRoute
import com.example.navigation.MeetingScheduleRoute
import com.example.navigation.MyCommentsRoute
import com.example.navigation.MyPageScreenRoute
import com.example.navigation.MyPostsRoute
import com.example.navigation.UserInfoRoute

fun NavController.navigateMyPage() {
    navigate(MyPageScreenRoute) {
    }
}

fun NavController.navigateEditProfile() {
    navigate(EditProfileRoute)
}

fun NavController.navigateFullMeeting(isBookmarkPage: Boolean) {
    navigate(FullMeetingRoute(isBookmarkPage))
}

fun NavController.navigateFullJoinMeeting() {
    navigate(FullJoinMeetingRoute)
}

fun NavController.navigateFullBookmarkMeeting() {
    navigate(FullBookmarkMeetingRoute)
}

fun NavController.navigateMeetingSchedule() {
    navigate(MeetingScheduleRoute)
}

fun NavController.navigateMyPosts() {
    navigate(MyPostsRoute)
}

fun NavController.navigateMyComments() {
    navigate(MyCommentsRoute)
}

fun NavController.navigateUserInfo() {
    navigate(UserInfoRoute)
}

fun NavGraphBuilder.myPageNavGraph(
    snackBarHostState: SnackbarHostState,
    onShowSnackBar: (String) -> Unit,
    onEventClick: (Int) -> Unit,
    onEditProfileButtonClick: () -> Unit,
    onFullMeetingButtonClick: (Boolean) -> Unit,
    onNavigationButtonClick: () -> Unit,
    onUserInfoNavButtonClick: () -> Unit,
    onJoinMeetingNavButtonClick: () -> Unit,
    onBookmarkMeetingNavButtonClick: () -> Unit,
    onMeetingScheduleNavButtonClick: () -> Unit,
    onPostsNavButtonClick: () -> Unit,
    onCommentsNavButtonClick: () -> Unit,
    onEditProfileSuccess: () -> Unit,
) {
    composable<MyPageScreenRoute> {
        MyPageScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onEventClick = { eventId -> onEventClick(eventId) },
            onEditProfileButtonClick = onEditProfileButtonClick,
            onFullMeetingButtonClick = { isBookmarkPage -> onFullMeetingButtonClick(isBookmarkPage) },
            onUserInfoNavButtonClick = onUserInfoNavButtonClick,
            onJoinMeetingNavButtonClick = onJoinMeetingNavButtonClick,
            onBookmarkMeetingNavButtonClick = onBookmarkMeetingNavButtonClick,
            onMeetingScheduleNavButtonClick = onMeetingScheduleNavButtonClick,
            onPostsNavButtonClick = onPostsNavButtonClick,
            onCommentsNavButtonClick = onCommentsNavButtonClick,
        )
    }
    composable<EditProfileRoute> {
        EditProfileScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onNavigationButtonClick = onNavigationButtonClick,
            onEditProfileSuccess = onEditProfileSuccess,
        )
    }

    composable<FullMeetingRoute> {
        FullMeetingScreen(
            snackBarHostState = snackBarHostState,
            onShowSnackBar = onShowSnackBar,
            onNavigationButtonClick = onNavigationButtonClick,
            onEventClick = { eventId -> onEventClick(eventId) },
        )
    }

    composable<UserInfoRoute> {
        UserInfoScreen(
            onNavigationButtonClick = onNavigationButtonClick
        )
    }

    composable<FullJoinMeetingRoute> {
        JoinMeetingScreen(
            onNavigationButtonClick = onNavigationButtonClick,
            onEventClick = { eventId -> onEventClick(eventId) }
        )
    }

    composable<FullBookmarkMeetingRoute> {
        BookmarkMeetingScreen(
            onNavigationButtonClick = onNavigationButtonClick,
            onEventClick = { eventId -> onEventClick(eventId) }
        )
    }

    composable<MeetingScheduleRoute> {
        MeetingScheduleScreen(
            onNavigationButtonClick = onNavigationButtonClick
        )
    }

    composable<MyPostsRoute> {
        MyPostsScreen(
            onNavigationButtonClick = onNavigationButtonClick
        )
    }

    composable<MyCommentsRoute> {
        MyCommentsScreen(
            onNavigationButtonClick = onNavigationButtonClick
        )
    }
}

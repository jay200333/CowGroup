package com.example.model

data class MyPageInfo(
    val userInfo: MyPageUserInfo,
    val eventList: List<MyPageEvent>,
    val bookmarkList: List<MyPageEvent>
)

data class MyPageUserInfo(
    val name: String,
    val gender: String,
    val birth: String,
    val mbti: String
)

data class MyPageEvent(
    val id: Int,
    val author: String,
    val eventName: String,
    val eventDate: String,
    val isBookMarked: Boolean,
    val applicants: Int,
    val capacity: Int
)
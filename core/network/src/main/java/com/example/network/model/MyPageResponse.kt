package com.example.network.model

import com.example.model.MyPageEvent
import com.example.model.MyPageInfo
import com.example.model.MyPageUserInfo

data class MyPageResponse(
    val userDto: UserDto,
    val userEventList: List<MyPageEventDto>,
    val bookmarkList: List<MyPageEventDto>
)

data class UserDto(
    val name: String,
    val gender: String,
    val birth: String?,
    val mbti: String?
)

data class MyPageEventDto(
    val id: Int,
    val author: String,
    val eventName: String,
    val eventDate: String,
    val status: String,
    val applicants: Int,
    val capacity: Int
)

fun MyPageResponse.toMyPageInfo(): MyPageInfo = MyPageInfo(
    userInfo = userDto.toUserInfo(),
    eventList = userEventList.map { it.toMyPageEvent() },
    bookmarkList = bookmarkList.map { it.toMyPageEvent() }
)

fun UserDto.toUserInfo(): MyPageUserInfo = MyPageUserInfo(
    name = name,
    gender = gender,
    birth = birth ?: "",
    mbti = mbti ?: ""
)

fun MyPageEventDto.toMyPageEvent(): MyPageEvent = MyPageEvent(
    id = id,
    author = author,
    eventName = eventName,
    eventDate = eventDate,
    isBookMarked = status == "BOOKMARK",
    applicants = applicants,
    capacity = capacity
)

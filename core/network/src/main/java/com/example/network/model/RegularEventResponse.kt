package com.example.network.model

import com.example.model.CreateRegularMeeting

data class RegularEventResponse(
    val id: Int,
    val name: String,
    val location: String,
    val dateTime: String,
    val capacity: Int
)

fun RegularEventResponse.toCreateRegularMeeting(): CreateRegularMeeting = CreateRegularMeeting(
    name = name,
    location = location,
    dateTime = dateTime,
    capacity = capacity
)
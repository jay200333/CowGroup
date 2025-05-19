package com.example.data.repository

import com.example.model.CreateRegularMeeting

interface RegularMeetingRepository {
    suspend fun createRegularMeeting(eventId: Int, regularMeeting: CreateRegularMeeting)

    suspend fun getRegularMeeting(regularId: Int): CreateRegularMeeting

    suspend fun updateJoinRegularMeeting(regularEventId: Int, hasJoined: Boolean)
}
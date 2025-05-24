package com.example.data.repository

import com.example.model.CreateRegularMeeting
import com.example.model.EventMember

interface RegularMeetingRepository {
    suspend fun createRegularMeeting(eventId: Int, regularMeeting: CreateRegularMeeting)

    suspend fun getRegularMeeting(regularId: Int): CreateRegularMeeting

    suspend fun editRegularMeeting(regularId: Int, regularMeeting: CreateRegularMeeting)

    suspend fun deleteRegularMeeting(regularId: Int)

    suspend fun updateJoinRegularMeeting(regularEventId: Int, hasJoined: Boolean)

    suspend fun getRegularMemberList(regularId: Int): EventMember
}
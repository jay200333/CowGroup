package com.example.data.repository

import androidx.paging.PagingData
import com.example.model.CreateRegularMeeting
import com.example.model.EventMember
import com.example.model.RegularEvent
import kotlinx.coroutines.flow.Flow

interface RegularMeetingRepository {
    fun getPagingRegularEvents(pageSize: Int, eventId: Int): Flow<PagingData<RegularEvent>>

    suspend fun createRegularMeeting(eventId: Int, regularMeeting: CreateRegularMeeting)

    suspend fun getRegularMeeting(regularId: Int): CreateRegularMeeting

    suspend fun editRegularMeeting(regularId: Int, regularMeeting: CreateRegularMeeting)

    suspend fun deleteRegularMeeting(regularId: Int)

    suspend fun updateJoinRegularMeeting(regularEventId: Int, participationId: Int?): Int

    suspend fun getRegularMemberList(regularId: Int): EventMember
}
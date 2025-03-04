package com.example.data.repository

import androidx.paging.PagingData
import com.example.model.CreateEvent
import com.example.model.DetailEvent
import com.example.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getPagingHomeEvents(pageSize: Int): Flow<PagingData<Event>>

    fun getPagingParticipateEvents(pageSize: Int): Flow<PagingData<Event>>

    fun getPagingBookmarkEvents(pageSize: Int): Flow<PagingData<Event>>

    suspend fun getEvents(page: Int, size: Int): Flow<List<Event>>

    suspend fun updateBookmark(eventId: Int, isBookmarked: Boolean)

    suspend fun createMeeting(createEvent: CreateEvent)

    suspend fun getEventDetail(eventId: Int): DetailEvent

    suspend fun updateJoinEvent(eventId: Int, hasJoined: Boolean)

    suspend fun editEvent(eventId: Int, event: CreateEvent)

    suspend fun deleteEvent(eventId: Int)

    suspend fun getParticipateEvents(page: Int, size: Int): Flow<List<Event>>

    suspend fun getBookmarkEvents(page: Int, size: Int): Flow<List<Event>>
}

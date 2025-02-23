package com.example.data.repository

import androidx.paging.PagingData
import com.example.model.CreateEvent
import com.example.model.DetailEvent
import com.example.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getPagingEvents(pageSize: Int): Flow<PagingData<Event>>

    suspend fun getEvents(page: Int, size: Int): Flow<List<Event>>

    suspend fun updateBookmark(eventId: Int, isBookmarked: Boolean)

    suspend fun createMeeting(createEvent: CreateEvent)

    suspend fun getEventDetail(eventId: Int): Flow<DetailEvent>

    suspend fun joinEvent(eventId: Int)
}

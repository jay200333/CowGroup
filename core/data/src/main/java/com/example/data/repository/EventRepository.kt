package com.example.data.repository

import com.example.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun getEvents(): Flow<List<Event>>

    suspend fun updateBookmark(eventId: Int, isBookmarked: Boolean): Boolean
}

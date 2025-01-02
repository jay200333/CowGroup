package com.example.data.repository

import com.example.common.Resource
import com.example.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    suspend fun getEvents(): Flow<Resource<List<Event>>>

    suspend fun updateBookmark(eventId: Int, isBookmarked: Boolean): Resource<Unit>
}

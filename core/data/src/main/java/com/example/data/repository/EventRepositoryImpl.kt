package com.example.data.repository

import com.example.common.Resource
import com.example.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
// private val eventService: CowGroupService,
) : EventRepository {
    override suspend fun getEvents(): Flow<Resource<List<Event>>> = flow {
        try {
            emit(Resource.Loading())
            // val networkEvents = eventService.getEvents()
            // val events = networkEvents.map { it.toEvent() }
            val dummyEventList: List<Event> = listOf(
                Event(1, "test1", "test1", "2025-12-7", "2024-12-31", 10, 100, false),
                Event(2, "test2", "test2", "2025-11-3", "2024-12-30", 20, 200, true),
                Event(3, "test3", "test3", "2025-12-2", "2024-12-29", 30, 300, true),
            )
            emit(Resource.Success(dummyEventList))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }

    override suspend fun updateBookmark(eventId: Int, isBookmarked: Boolean): Resource<Unit> {
        TODO("Not yet implemented")
    }
}

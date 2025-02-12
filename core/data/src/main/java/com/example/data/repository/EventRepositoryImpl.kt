package com.example.data.repository

import com.example.model.CreateEvent
import com.example.model.Event
import com.example.network.retrofit.CowGroupApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class EventRepositoryImpl @Inject constructor(
    private val api: CowGroupApi,
) : EventRepository {
    override suspend fun getEvents(): Flow<List<Event>> = flow {
        try {
            // val networkEvents = eventService.getEvents()
            // val events = networkEvents.map { it.toEvent() }
            val dummyEventList: List<Event> = listOf(
                Event(1, "test1", "test1", "2025-12-7", "2024-12-31", 10, 100, false),
                Event(2, "test2", "test2", "2025-11-3", "2024-12-30", 20, 200, true),
                Event(3, "test3", "test3", "2025-12-2", "2024-12-29", 30, 300, true),
            )
            emit(dummyEventList)
        } catch (e: IOException) {
            "Couldn't reach server. Check your internet connection"
        }
    }

    override suspend fun updateBookmark(eventId: Int, isBookmarked: Boolean): Boolean = try {
        true
    } catch (e: IOException) {
        false
    }

    override suspend fun createMeeting(createEvent: CreateEvent) {
        try {
            api.createMeeting(createEvent = createEvent)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}

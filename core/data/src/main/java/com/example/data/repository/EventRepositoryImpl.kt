package com.example.data.repository

import com.example.model.CreateEvent
import com.example.model.Event
import com.example.network.model.toEvent
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
            val networkEvents = api.getEvents(0,10)
            val events = networkEvents.data.content.map { it.toEvent() }
            emit(events)
        } catch (e: IOException) {
            throw e
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

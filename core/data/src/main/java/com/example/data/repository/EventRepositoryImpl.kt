package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.paging.EventPagingSource
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

    override fun getPagingEvents(pageSize: Int): Flow<PagingData<Event>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { EventPagingSource(api) }
        ).flow
    }

    override suspend fun getEvents(page: Int, size: Int): Flow<List<Event>> = flow {
        try {
            val networkEvents = api.getEvents(page, size)
            val events = networkEvents.data.content.map { it.toEvent() }
            emit(events)
        } catch (e: IOException) {
            throw e
        }
    }

    override suspend fun updateBookmark(eventId: Int, isBookmarked: Boolean) {
        try {
            if (isBookmarked) {
                api.deleteBookmark(eventId)
            } else {
                api.addBookmark(eventId)
            }
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
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

    companion object {
        private const val INITIAL_LOAD_SIZE = 10
    }
}

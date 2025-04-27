package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.paging.BookmarkEventPagingSource
import com.example.data.paging.HomeEventPagingSource
import com.example.data.paging.ParticipateEventPagingSource
import com.example.model.CreateMeeting
import com.example.model.DetailEvent
import com.example.model.Event
import com.example.network.model.toDetailEvent
import com.example.network.model.toEvent
import com.example.network.retrofit.CowGroupApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class EventRepositoryImpl @Inject constructor(
    private val api: CowGroupApi,
) : EventRepository {

    override fun getPagingHomeEvents(pageSize: Int): Flow<PagingData<Event>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HomeEventPagingSource(api) }
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

    override suspend fun getParticipateEvents(page: Int, size: Int): Flow<List<Event>> = flow {
        try {
            val participateEvents = api.getParticipateEvents(page, size)
            val events = participateEvents.data.participatingEvents.map { it.toEvent() }
            emit(events)
        } catch (e: IOException) {
            throw e
        }
    }

    override fun getPagingParticipateEvents(pageSize: Int): Flow<PagingData<Event>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ParticipateEventPagingSource(api) }
        ).flow
    }

    override suspend fun getBookmarkEvents(page: Int, size: Int): Flow<List<Event>> = flow {
        try {
            val bookmarkEvents = api.getBookmarkEvents(page, size)
            val events = bookmarkEvents.data.bookmarkEvents.map { it.toEvent() }
            emit(events)
        } catch (e: IOException) {
            throw e
        }
    }

    override fun getPagingBookmarkEvents(pageSize: Int): Flow<PagingData<Event>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BookmarkEventPagingSource(api) }
        ).flow
    }

    override suspend fun getEventDetail(eventId: Int): DetailEvent =
        try {
            val response = api.getEventDetail(eventId)
            val detailEvent = response.data.toDetailEvent()
            detailEvent
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }

    override suspend fun updateJoinEvent(eventId: Int, hasJoined: Boolean) {
        try {
            if (hasJoined) {
                api.unJoinEvent(eventId)
            } else {
                api.joinEvent(eventId)
            }
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun editEvent(eventId: Int, event: CreateMeeting) {
        try {
            api.editEvent(eventId, event)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteEvent(eventId: Int) {
        try {
            api.deleteEvent(eventId)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
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

    override suspend fun createMeeting(createMeeting: CreateMeeting) {
        try {
            val filePart = createMeeting.file?.let { file ->
                val fileRequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("file", file.name, fileRequestBody)
            }
            val namePart = createMeeting.name.toRequestBody("text/plain".toMediaTypeOrNull())
            val categoryPart = createMeeting.category.name.toRequestBody("text/plain".toMediaTypeOrNull())
            val capacityPart = createMeeting.capacity.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val contentPart = createMeeting.content.toRequestBody("text/plain".toMediaTypeOrNull())

            api.createMeeting(
                file = filePart,
                name = namePart,
                category = categoryPart,
                capacity = capacityPart,
                content = contentPart
            )
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

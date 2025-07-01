package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.paging.RegularMeetingPagingSource
import com.example.data.paging.RegularMeetingSearchPagingSource
import com.example.model.CreateRegularMeeting
import com.example.model.EventMember
import com.example.model.RegularEvent
import com.example.model.SearchRegularEvent
import com.example.model.SearchRegularMeetingRequest
import com.example.network.model.toCreateRegularMeeting
import com.example.network.model.toEventMember
import com.example.network.retrofit.CowGroupApi
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

internal class RegularMeetingRepositoryImpl @Inject constructor(
    private val api: CowGroupApi
) : RegularMeetingRepository {
    override fun getPagedRegularEventsBySearch(
        pageSize: Int,
        searchRequest: SearchRegularMeetingRequest
    ): Flow<PagingData<SearchRegularEvent>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RegularMeetingSearchPagingSource(api, searchRequest) }
        ).flow
    }
    override fun getPagingRegularEvents(pageSize: Int, eventId: Int): Flow<PagingData<RegularEvent>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = INITIAL_LOAD_SIZE,
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RegularMeetingPagingSource(api, eventId) }
        ).flow
    }

    override suspend fun createRegularMeeting(eventId: Int, regularMeeting: CreateRegularMeeting) {
        try {
            api.createRegularMeeting(eventId, regularMeeting)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getRegularMeeting(regularId: Int): CreateRegularMeeting =
        try {
            val response = api.getRegularMeeting(regularId)
            val regularMeeting = response.data.toCreateRegularMeeting()
            regularMeeting
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }

    override suspend fun editRegularMeeting(regularId: Int, regularMeeting: CreateRegularMeeting) {
        try {
            api.editRegularMeeting(regularId, regularMeeting)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteRegularMeeting(regularId: Int) {
        try {
            api.deleteRegularMeeting(regularId)
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateJoinRegularMeeting(regularEventId: Int, participationId: Int?): Int =
        try {
            var id = 0
            if (participationId != 0 && participationId != null) {
                api.unJoinRegularMeeting(participationId)
            } else {
                val result = api.joinRegularMeeting(regularEventId).data
                id = result
            }
            id
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }


    override suspend fun getRegularMemberList(regularId: Int): EventMember {
        try {
            val response = api.getRegularMemberList(regularId)
            val regularMeetingMember = response.data.toEventMember()
            return regularMeetingMember
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPagedRegularMeetingCount(searchRequest: SearchRegularMeetingRequest): Int {
        try {
            val response = api.getSearchRegularMeetingCount(searchRequest)
            return response.data
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
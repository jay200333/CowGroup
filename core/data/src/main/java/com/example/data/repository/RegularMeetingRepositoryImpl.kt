package com.example.data.repository

import com.example.model.CreateRegularMeeting
import com.example.network.model.toCreateRegularMeeting
import com.example.network.retrofit.CowGroupApi
import retrofit2.HttpException
import javax.inject.Inject

internal class RegularMeetingRepositoryImpl @Inject constructor(
    private val api: CowGroupApi
) : RegularMeetingRepository {

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

    override suspend fun updateJoinRegularMeeting(regularEventId: Int, hasJoined: Boolean) {
        try {
            if (hasJoined) {
                api.unJoinRegularMeeting(regularEventId)
            } else {
                api.joinRegularMeeting(regularEventId)
            }
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}
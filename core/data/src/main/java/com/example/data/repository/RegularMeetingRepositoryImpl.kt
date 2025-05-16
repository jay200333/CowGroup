package com.example.data.repository

import com.example.model.CreateRegularMeeting
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
}
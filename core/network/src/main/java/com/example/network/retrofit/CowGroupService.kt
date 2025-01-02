package com.example.network.retrofit

import com.example.network.model.NetworkEvent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface CowGroupService {
    @GET("/events")
    suspend fun getEvents(): List<NetworkEvent>

    @PATCH("/events/{eventId}/bookmark")
    suspend fun updateBookmark(
        @Path("eventId") eventId: Int,
        @Query("isBookmarked") isBookmarked: Boolean,
    ): Response<Unit>
}

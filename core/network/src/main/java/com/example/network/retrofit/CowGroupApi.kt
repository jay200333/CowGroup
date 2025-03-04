package com.example.network.retrofit

import com.example.model.CreateEvent
import com.example.model.LoginInfo
import com.example.model.Profile
import com.example.model.SignUpInfo
import com.example.network.model.ApiResponse
import com.example.network.model.CheckDuplicateResponse
import com.example.network.model.DetailEventResponse
import com.example.network.model.MyPageResponse
import com.example.network.model.PagingBookmarkEventResponse
import com.example.network.model.PagingHomeEventResponse
import com.example.network.model.PagingParticipatingEventResponse
import com.example.network.model.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CowGroupApi {
    @POST("/users/signUp")
    suspend fun signUp(@Body signUpInfo: SignUpInfo): ApiResponse<Unit>

    @Headers("Content-Type: application/json")
    @POST("/users/login")
    suspend fun login(@Body loginInfo: LoginInfo): Response<ApiResponse<Unit>>

    @GET("/users")
    suspend fun getProfile(): ApiResponse<ProfileResponse>

    @PATCH("/users")
    suspend fun editProfile(@Body profile: Profile): ApiResponse<Unit>

    @GET("/users/username/{username}")
    suspend fun checkUsername(@Path("username") username: String): ApiResponse<CheckDuplicateResponse>

    @GET("/users/email/{email}")
    suspend fun checkEmail(@Path("email") email: String): ApiResponse<CheckDuplicateResponse>

    @GET("/users/my-page")
    suspend fun getMyPage(): ApiResponse<MyPageResponse>


    @GET("/events")
    suspend fun getEvents(@Query("page") page: Int, @Query("size") size: Int): ApiResponse<PagingHomeEventResponse>

    @POST("/events")
    suspend fun createMeeting(@Body createEvent: CreateEvent): ApiResponse<Unit>

    @POST("/events/{event-id}/join")
    suspend fun joinEvent(@Path("event-id") eventId: Int): ApiResponse<Unit>

    @DELETE("/events/{event-id}/join")
    suspend fun unJoinEvent(@Path("event-id") eventId: Int): ApiResponse<Unit>

    @GET("/events/{event-id}")
    suspend fun getEventDetail(@Path("event-id") eventId: Int): ApiResponse<DetailEventResponse>

    @DELETE("/events/{event-id}")
    suspend fun deleteEvent(@Path("event-id") eventId: Int): ApiResponse<Unit>

    @PATCH("/events/{event-id}")
    suspend fun editEvent(@Path("event-id") eventId: Int, @Body createEvent: CreateEvent): ApiResponse<Unit>

    @POST("/bookmarks/{event-id}")
    suspend fun addBookmark(@Path("event-id") eventId: Int): ApiResponse<Unit>

    @PATCH("/bookmarks/{event-id}")
    suspend fun deleteBookmark(@Path("event-id") eventId: Int): ApiResponse<Unit>

    @GET("/events/participating")
    suspend fun getParticipateEvents(@Query("page") page: Int, @Query("size") size: Int): ApiResponse<PagingParticipatingEventResponse>

    @GET("/events/bookmarks")
    suspend fun getBookmarkEvents(@Query("page") page: Int, @Query("size") size: Int): ApiResponse<PagingBookmarkEventResponse>












}
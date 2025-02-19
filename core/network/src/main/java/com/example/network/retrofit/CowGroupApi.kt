package com.example.network.retrofit

import com.example.model.CreateEvent
import com.example.model.LoginInfo
import com.example.model.Profile
import com.example.model.SignUpInfo
import com.example.network.model.ApiResponse
import com.example.network.model.CheckDuplicateResponse
import com.example.network.model.PagingEventResponse
import com.example.network.model.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CowGroupApi {
    @GET("/events")
    suspend fun getEvents(@Query("page") page: Int, @Query("size") size: Int): ApiResponse<PagingEventResponse>

    @POST("/bookmarks/{event-id}")
    suspend fun updateBookmark(@Path("event-id") eventId: Int): Response<Any>

    @POST("/users/signUp")
    suspend fun signUp(@Body signUpInfo: SignUpInfo): ApiResponse<Unit>

    @GET("/users/username/{username}")
    suspend fun checkUsername(@Path("username") username: String): ApiResponse<CheckDuplicateResponse>

    @GET("/users/email/{email}")
    suspend fun checkEmail(@Path("email") email: String): ApiResponse<CheckDuplicateResponse>

    @Headers("Content-Type: application/json")
    @POST("/users/login")
    suspend fun login(@Body loginInfo: LoginInfo): Response<ApiResponse<Unit>>

    @POST("/events")
    suspend fun createMeeting(@Body createEvent: CreateEvent): ApiResponse<Unit>

    @GET("/users")
    suspend fun getProfile(): ApiResponse<ProfileResponse>

    @PATCH("/users")
    suspend fun editProfile(@Body profile: Profile): ApiResponse<Unit>
}
package com.example.network.retrofit

import com.example.model.DetailEvent
import com.example.model.LoginInfo
import com.example.model.SignUpInfo
import com.example.network.model.ApiResponse
import com.example.network.model.CheckDuplicateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CowGroupApi {
    @GET("/events?")
    suspend fun getEvents(@Query("page") page: Int, @Query("size") size: Int)

    @POST("/bookmarks/{event-id}")
    suspend fun updateBookmark(@Path("event-id") eventId: Int): Response<Any>

    @POST("/users/signUp")
    suspend fun signUp(@Body signUpInfo: SignUpInfo): ApiResponse<Unit>

    @GET("/users/username/{username}")
    suspend fun checkUsername(@Path("username") username: String): ApiResponse<CheckDuplicateResponse>

    @GET("/users/email/{email}")
    suspend fun checkEmail(@Path("email") email: String): ApiResponse<CheckDuplicateResponse>

    @POST("/users/login")
    suspend fun login(@Body loginInfo: LoginInfo): Response<ApiResponse<Unit>>

    @POST("/events")
    suspend fun createMeeting(@Body detailEvent: DetailEvent): Boolean
}

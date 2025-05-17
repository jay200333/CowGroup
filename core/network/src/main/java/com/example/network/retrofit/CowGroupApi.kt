package com.example.network.retrofit

import com.example.model.CreateMeeting
import com.example.model.CreateRegularMeeting
import com.example.model.EmailCodeInfo
import com.example.model.LoginInfo
import com.example.model.Profile
import com.example.model.SignUpInfo
import com.example.network.model.ApiResponse
import com.example.network.model.CheckVerificationResponse
import com.example.network.model.DetailEventResponse
import com.example.network.model.MemberResponse
import com.example.network.model.MyPageResponse
import com.example.network.model.PagingBookmarkEventResponse
import com.example.network.model.PagingHomeEventResponse
import com.example.network.model.PagingParticipatingEventResponse
import com.example.network.model.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
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

    @GET("/users/verification/username/{username}")
    suspend fun checkUsername(@Path("username") username: String): ApiResponse<CheckVerificationResponse>

    @POST("/users/verification/email/{email}")
    suspend fun sendAuthCode(@Path("email") email: String): ApiResponse<CheckVerificationResponse>

    @POST("/users/verification/email/code")
    suspend fun checkAuthCode(@Body emailCodeInfo: EmailCodeInfo): ApiResponse<CheckVerificationResponse>

    @POST("/users/password-reset")
    suspend fun sendTempPassword(@Query ("email") email: String): ApiResponse<Unit>

    @GET("/users/my-page")
    suspend fun getMyPage(): ApiResponse<MyPageResponse>

    @GET("/events/{event-id}/participants")
    suspend fun getEventMemberList(@Path("event-id") eventId: Int): ApiResponse<MemberResponse>

    @GET("/events")
    suspend fun getEvents(@Query("page") page: Int, @Query("size") size: Int): ApiResponse<PagingHomeEventResponse>

    @Multipart
    @POST("/events")
    suspend fun createMeeting(
        @Part file: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("category") category: RequestBody,
        @Part("capacity") capacity: RequestBody,
        @Part("content") content: RequestBody): ApiResponse<Int>

    @POST("/events/{event-id}/participation")
    suspend fun joinEvent(@Path("event-id") eventId: Int): ApiResponse<Unit>

    @DELETE("/events/{event-id}/participation")
    suspend fun unJoinEvent(@Path("event-id") eventId: Int): ApiResponse<Unit>

    @GET("/events/{event-id}/regular")
    suspend fun getEventDetail(@Path("event-id") eventId: Int): ApiResponse<DetailEventResponse>

    @DELETE("/events/{event-id}")
    suspend fun deleteEvent(@Path("event-id") eventId: Int): ApiResponse<Unit>

    @PATCH("/events/{event-id}")
    suspend fun editEvent(@Path("event-id") eventId: Int, @Body createMeeting: CreateMeeting): ApiResponse<Unit>

    @POST("/bookmarks/{event-id}")
    suspend fun addBookmark(@Path("event-id") eventId: Int): ApiResponse<Unit>

    @PATCH("/bookmarks/{event-id}")
    suspend fun deleteBookmark(@Path("event-id") eventId: Int): ApiResponse<Unit>

    @GET("/events/participating")
    suspend fun getParticipateEvents(@Query("page") page: Int, @Query("size") size: Int): ApiResponse<PagingParticipatingEventResponse>

    @GET("/events/bookmarks")
    suspend fun getBookmarkEvents(@Query("page") page: Int, @Query("size") size: Int): ApiResponse<PagingBookmarkEventResponse>

    @POST("/events/{event-id}/regular")
    suspend fun createRegularMeeting(@Path("event-id") eventId: Int, @Body regularMeeting: CreateRegularMeeting): ApiResponse<Unit>

    @POST("/regular/{regular-id}/participation")
    suspend fun joinRegularMeeting(@Path("regular-id") regularId: Int): ApiResponse<Unit>

    @DELETE("/regular/participation/{participation-id}")
    suspend fun unJoinRegularMeeting(@Path("participation-id") participationId: Int): ApiResponse<Unit>
}
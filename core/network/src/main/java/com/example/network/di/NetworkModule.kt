package com.example.network.di

import android.util.Log
import com.example.datastore.TokenDataStore
import com.example.network.retrofit.CowGroupApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://43.202.229.168:8080"

    @Provides
    @Singleton
    fun okHttpClient(interceptor: AppInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            ).build()
    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): CowGroupApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(
                okHttpClient,
            ).build().create(CowGroupApi::class.java)
    }

    class AppInterceptor @Inject constructor(
        private val tokenDataStore: TokenDataStore,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val response = chain.proceed(originalRequest)
            Log.d("NetworkModule", "${response.headers}")
            val newToken = response.headers
            Log.d("333", "$newToken")
            return response


            //val accessToken = runBlocking { tokenDataStore.getToken() ?: "" }

            //// 헤더에 authentication라는 key로 JWT 를 넣어준다.
            //val newRequest = chain.request().newBuilder().addHeader("Authentication", accessToken).build()
            //return chain.proceed(newRequest)
        }
    }
}

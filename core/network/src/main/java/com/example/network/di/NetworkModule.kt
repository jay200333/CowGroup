package com.example.network.di

import com.example.network.retrofit.CowGroupService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideEventApi(): Retrofit =
        Retrofit.Builder().baseUrl("https://api.example.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
}

//@Provides
//@Singleton
//fun eventService(retrofit: Retrofit): CowGroupService = retrofit.create(CowGroupService::class.java)

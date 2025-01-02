package com.example.data.di

import com.example.data.repository.EventRepository
import com.example.data.repository.EventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository
}

package com.example.data.di

import com.example.data.repository.EventRepository
import com.example.data.repository.EventRepositoryImpl
import com.example.data.repository.PostRepository
import com.example.data.repository.PostRepositoryImpl
import com.example.data.repository.RegularMeetingRepository
import com.example.data.repository.RegularMeetingRepositoryImpl
import com.example.data.repository.SearchHistoryRepository
import com.example.data.repository.SearchHistoryRepositoryImpl
import com.example.data.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindEventRepository(eventRepositoryImpl: EventRepositoryImpl): EventRepository

    @Binds
    internal abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    internal abstract fun bindSearchHistoryRepository(searchHistoryRepositoryImpl: SearchHistoryRepositoryImpl): SearchHistoryRepository

    @Binds
    internal abstract fun bindRegularMeetingRepository(regularMeetingRepositoryImpl: RegularMeetingRepositoryImpl): RegularMeetingRepository

    @Binds
    internal abstract fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository
}

package com.example.database.di

import com.example.database.CowGroupDatabase
import com.example.database.RegularEventSearchHistoryDatabase
import com.example.database.dao.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    @Named("group")
    fun providesSearchHistoryDao(
        database: CowGroupDatabase
    ): SearchHistoryDao = database.searchHistoryDao()

    @Provides
    @Named("regular")
    fun providesRegularEventSearchHistoryDao(
        database: RegularEventSearchHistoryDatabase
    ): SearchHistoryDao = database.searchHistoryDao()
}

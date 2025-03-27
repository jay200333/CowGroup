package com.example.database.di

import com.example.database.CowGroupDatabase
import com.example.database.dao.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesSearchHistoryDao(
        database: CowGroupDatabase,
    ): SearchHistoryDao = database.searchHistoryDao()
}

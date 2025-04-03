package com.example.data.repository

import com.example.data.model.SearchHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    suspend fun insertSearchHistory(query: String)

    fun getRecentSearches(): Flow<List<SearchHistory>>

    suspend fun deleteSearchHistory(query: String)
}
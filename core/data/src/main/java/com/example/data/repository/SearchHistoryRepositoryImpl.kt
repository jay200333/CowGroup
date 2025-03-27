package com.example.data.repository

import android.util.Log
import com.example.common.DateUtil
import com.example.data.model.SearchHistory
import com.example.database.dao.SearchHistoryDao
import com.example.database.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) : SearchHistoryRepository {
    override suspend fun insertSearchHistory(query: String) {
        Log.d("SearchHistoryRepositoryImpl", "insertSearchHistory: $query")
        searchHistoryDao.insertSearchHistory(
            SearchHistoryEntity(
                query = query,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override fun getRecentSearches(): Flow<List<SearchHistory>> {
        return searchHistoryDao.getRecentSearches()
            .map { list ->
                list.map {
                    SearchHistory(
                        it.query,
                        DateUtil.timeStampFormat(it.timestamp)
                    )
                }
            }
    }

    override suspend fun deleteSearchHistory(query: String) {
        searchHistoryDao.deleteSearchHistory(query)
    }
}
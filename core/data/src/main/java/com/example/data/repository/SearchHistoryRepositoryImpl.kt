package com.example.data.repository

import com.example.common.DateUtil
import com.example.data.model.SearchHistory
import com.example.database.dao.SearchHistoryDao
import com.example.database.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

internal class SearchHistoryRepositoryImpl @Inject constructor(
    @Named("group") private val searchHistoryDao: SearchHistoryDao
) : SearchHistoryRepository {
    override suspend fun insertSearchHistory(query: String) {
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
                        DateUtil.convertMillisToDate(it.timestamp)
                    )
                }
            }
    }

    override suspend fun deleteSearchHistory(query: String) {
        searchHistoryDao.deleteSearchHistory(query)
    }
}
package com.example.data.repository

import com.example.common.DateUtil
import com.example.data.model.SearchHistory
import com.example.database.dao.SearchHistoryDao
import com.example.database.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

internal class RegularMeetingSearchHistoryRepositoryImpl @Inject constructor(
    @Named("regular") private val searchHistoryDao: SearchHistoryDao
) : RegularMeetingSearchHistoryRepository {
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
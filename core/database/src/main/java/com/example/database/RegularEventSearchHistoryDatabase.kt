package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.SearchHistoryDao
import com.example.database.model.SearchHistoryEntity

@Database(entities = [SearchHistoryEntity::class], version = 1)
abstract class RegularEventSearchHistoryDatabase: RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}
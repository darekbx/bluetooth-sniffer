package com.bluetoothsniffer.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bluetoothsniffer.repository.entities.MacCache

@Database(entities = arrayOf(MacCache::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        val DB_NAME = "app-database"
    }

    abstract fun macCacheDao(): MacCacheDao
}
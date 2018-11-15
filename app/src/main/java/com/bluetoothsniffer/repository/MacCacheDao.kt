package com.bluetoothsniffer.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bluetoothsniffer.repository.entities.MacCache

@Dao
interface MacCacheDao {

    @Query("SELECT vendor_name FROM mac_cache WHERE mac_short = :macShort")
    fun getCachedVendorName(macShort: String): String

    @Insert
    fun addVendorName(macCache: MacCache)
}
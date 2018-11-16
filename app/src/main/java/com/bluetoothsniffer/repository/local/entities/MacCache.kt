package com.bluetoothsniffer.repository.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mac_cache")
data class MacCache(
        @PrimaryKey(autoGenerate = true) var id: Long? = null,
        @ColumnInfo(name = "mac_short") var macShort: String,
        @ColumnInfo(name = "vendor_name") var vendorName: String
)
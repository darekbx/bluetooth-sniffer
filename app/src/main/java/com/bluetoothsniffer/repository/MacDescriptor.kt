package com.bluetoothsniffer.repository

import android.util.Log
import com.bluetoothsniffer.repository.local.MacCacheDao
import com.bluetoothsniffer.repository.local.entities.MacCache
import com.bluetoothsniffer.repository.remote.MacVendorProvider
import com.bluetoothsniffer.utils.MacShortener
import java.lang.Exception

class MacDescriptor(
        private val macVendorProvider: MacVendorProvider,
        private val macCacheDao: MacCacheDao,
        private val macShortener: MacShortener) {

    fun resolveDeviceManufacturer(macAddress: String): String? {
        val macShorted = macShortener.createShortMac(macAddress)
        return macShorted?.let { macShorted ->
            val cachedName = macCacheDao.getCachedVendorName(macShorted)
            return when (cachedName) {
                null -> fetchVendorName(macShorted)
                else -> cachedName
            }
        } ?: null
    }

    private fun fetchVendorName(macShorted: String): String? {
        try {
            val wrapper = macVendorProvider.requestVendorName(macShorted)
            when (wrapper) {
                null -> return null
                else -> {
                    val vendorName = wrapper.data.organizationName
                    macCacheDao.addVendorName(MacCache(macShort = macShorted, vendorName = vendorName))
                    return vendorName
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
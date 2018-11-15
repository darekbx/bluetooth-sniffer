package com.bluetoothsniffer.utils

import android.text.TextUtils

class MacShortener {

    fun createShortMac(macAddress: String): String? {
        val chunks = macAddress.split(':')
        if (chunks.size != 6) return null
        return TextUtils.join("", chunks.subList(0, 3))
    }
}
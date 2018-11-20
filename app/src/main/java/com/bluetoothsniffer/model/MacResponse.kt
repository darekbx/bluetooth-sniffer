package com.bluetoothsniffer.model

import com.bluetoothsniffer.repository.remote.MacVendorProvider

data class MacResponse(
    val wrapper: MacVendorWrapper? = null,
    val status: MacVendorProvider.Status
)
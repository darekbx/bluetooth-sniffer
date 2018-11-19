package com.bluetoothsniffer.model

import android.bluetooth.le.ScanResult
import androidx.databinding.ObservableField

data class ScanResultWrapper(val scanResult: ScanResult,
                             val macVendorName: ObservableField<String>)
package com.bluetoothsniffer.model

import android.bluetooth.le.ScanResult
import androidx.databinding.ObservableField
import com.bluetoothsniffer.R

data class ScanResultWrapper(val scanResult: ScanResult,
                             val macVendorName: ObservableField<String>) {

    fun signalStrengthIcon() =
            when (scanResult.rssi) {
                in -60..0 -> R.drawable.ic_signal_4
                in -71..-61 -> R.drawable.ic_signal_3
                in -90..-71 -> R.drawable.ic_signal_2
                else -> R.drawable.ic_signal_1
            }
}
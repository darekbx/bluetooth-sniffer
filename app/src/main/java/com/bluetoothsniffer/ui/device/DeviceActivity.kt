package com.bluetoothsniffer.ui.device

import android.bluetooth.le.ScanResult
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bluetoothsniffer.R
import com.bluetoothsniffer.databinding.ActivityDeviceBinding

class DeviceActivity : AppCompatActivity() {

    companion object {
        val SCAN_RESULT_KEY = "scanResult"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDeviceBinding>(this, R.layout.activity_device).apply {
            scanResult = scanResultFromIntent()
        }

        // In Oreo: connectable, tx, battery level?
    }

    fun scanResultFromIntent() = intent.getParcelableExtra<ScanResult>(SCAN_RESULT_KEY)
}
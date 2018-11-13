package com.bluetoothsniffer.bluetooth

import android.bluetooth.BluetoothManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListDevicesFactory(val bluetoothManager: BluetoothManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListDevices(bluetoothManager) as T
    }
}
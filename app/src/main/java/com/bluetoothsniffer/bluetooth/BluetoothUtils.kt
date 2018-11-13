package com.bluetoothsniffer.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.provider.Settings

class BluetoothUtils {

    fun isBluetoothEnabled() = BluetoothAdapter.getDefaultAdapter().isEnabled

    fun forceEnableBluetooth(context: Context) {
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
        context.startActivity(intent)
    }
}
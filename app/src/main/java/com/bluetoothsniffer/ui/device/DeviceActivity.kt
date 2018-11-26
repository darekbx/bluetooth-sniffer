package com.bluetoothsniffer.ui.device

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanResult
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bluetoothsniffer.R
import com.bluetoothsniffer.databinding.ActivityDeviceBinding
import kotlinx.android.synthetic.main.activity_device.*

class DeviceActivity : AppCompatActivity() {

    companion object {
        val SCAN_RESULT_KEY = "scanResult"
    }

    private var isDiscovering = false
    private var gatt: BluetoothGatt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDeviceBinding>(this, R.layout.activity_device).apply {
            scanResult = scanResultFromIntent()
        }

        // In Oreo: connectable, tx, battery level?
    }

    fun scanResultFromIntent() = intent.getParcelableExtra<ScanResult>(SCAN_RESULT_KEY)

    fun onDiscoverClick(v: View) {
        when (isDiscovering) {
            true -> {
                discover_action.setImageResource(R.drawable.ic_discover)
                discover_progress.visibility = View.GONE
                dispose()
            }
            else -> {
                discover_action.setImageResource(R.drawable.ic_stop)
                discover_progress.visibility = View.VISIBLE
                connectDeviceAndDiscover()
            }
        }
        isDiscovering = !isDiscovering
    }

    fun connectDeviceAndDiscover() {
        gatt = scanResultFromIntent().device.connectGatt(this, false, gattCallback)
    }

    fun dispose() {
        gatt?.disconnect()
        gatt?.close()
    }

    fun discoverDevice() {
    }

    private fun updateStatus(status: String) {
        runOnUiThread { status_label.text = status }
    }

    val gattCallback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            val stateString = translateState(status)
            updateStatus(stateString)

            when (status) {
                BluetoothProfile.STATE_CONNECTED -> discoverDevice()
                BluetoothProfile.STATE_DISCONNECTED -> reconnect()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
        }

        override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
            super.onCharacteristicRead(gatt, characteristic, status)
        }

        override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
            super.onCharacteristicWrite(gatt, characteristic, status)
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
            super.onCharacteristicChanged(gatt, characteristic)
        }

        private fun reconnect() {
            //dispose()
            //connectDeviceAndDiscover()
        }

        private fun translateState(state: Int) =
                getString(when (state) {
                    BluetoothProfile.STATE_CONNECTED -> R.string.state_connected
                    BluetoothProfile.STATE_CONNECTING -> R.string.state_connecting
                    BluetoothProfile.STATE_DISCONNECTED -> R.string.state_disconnected
                    BluetoothProfile.STATE_DISCONNECTING -> R.string.state_disconnecting
                    else -> R.string.state_unknown
                })
    }
}
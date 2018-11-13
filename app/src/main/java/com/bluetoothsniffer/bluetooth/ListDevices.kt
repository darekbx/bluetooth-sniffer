package com.bluetoothsniffer.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bluetoothsniffer.notifyObserver


class ListDevices(val bluetoothManager: BluetoothManager): ViewModel() {

    private val deviceAddresses = mutableListOf<String>()
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var leScanner: BluetoothLeScanner? = null

    var isScanning = false
    var results = MutableLiveData<MutableList<ScanResult>>()
    var error = MutableLiveData<Int>()

    fun startStop() {
        when (isScanning) {
            true -> stop()
            else -> start()
        }
        isScanning = !isScanning
    }

    fun start() {
        bluetoothAdapter = bluetoothManager.adapter
        results.value = mutableListOf()
        leScanner = bluetoothAdapter?.bluetoothLeScanner
        leScanner?.startScan(scanCallback)
    }

    fun stop() {
        leScanner?.stopScan(scanCallback)
    }

    fun uniqueCount() = deviceAddresses.size

    private val scanCallback = object : ScanCallback() {

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            error.value = errorCode
        }

        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result?.run {
                val deviceAddress = this.device.address
                if (!deviceAddresses.contains(deviceAddress)) {
                    results.value?.add(this)
                    results.notifyObserver()
                    deviceAddresses.add(deviceAddress)
                }
            }
        }
    }
}
package com.bluetoothsniffer.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bluetoothsniffer.notifyObserver

/*

        curl -G "https://api.macvendors.com/v1/lookup/FC:FB:FB:01:FA:21" \
             -H "Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5c..."

eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJtYWN2ZW5kb3JzIiwiZXhwIjoxODU2NzI1NDQ0LCJpYXQiOjE1NDIyMjk0NDQsImlzcyI6Im1hY3ZlbmRvcnMiLCJqdGkiOiI3ZTFhNmRhNS0xYjkwLTQ1YjAtYjBmNS1jYmYyMzY1NTBjMTMiLCJuYmYiOjE1NDIyMjk0NDMsInN1YiI6IjkxMCIsInR5cCI6ImFjY2VzcyJ9.d91peGrdkB3NiMnUtF2n03xEaVXYqjtWBuacw02xqS2Mi4mbv-wI9IOU4oLjhcP4QNjsH9VLlBM38cbZnNHfXw

*/
class ListDevices(val bluetoothManager: BluetoothManager): ViewModel() {

    private val deviceAddresses = mutableListOf<String>()
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var leScanner: BluetoothLeScanner? = null

    private var _results = MutableLiveData<MutableList<ScanResult>>()
    private var _error = MutableLiveData<Int>()

    var isScanning = false
    var results: LiveData<MutableList<ScanResult>> = _results
    var error: LiveData<Int> = _error

    fun startStop() {
        when (isScanning) {
            true -> stop()
            else -> start()
        }
        isScanning = !isScanning
    }

    fun start() {
        bluetoothAdapter = bluetoothManager.adapter
        _results.value = mutableListOf()
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
            _error.value = errorCode
        }

        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            result?.run {
                val deviceAddress = this.device.address
                if (!deviceAddresses.contains(deviceAddress)) {
                    _results.value?.add(this)
                    _results.notifyObserver()
                    deviceAddresses.add(deviceAddress)
                }
            }
        }
    }
}
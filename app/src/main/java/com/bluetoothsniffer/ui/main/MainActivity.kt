package com.bluetoothsniffer.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluetoothsniffer.BluetoothSnifferApplication
import com.bluetoothsniffer.R
import com.bluetoothsniffer.bluetooth.BluetoothUtils
import com.bluetoothsniffer.bluetooth.ListDevices
import com.bluetoothsniffer.bluetooth.ListDevicesFactory
import com.bluetoothsniffer.permissons.PermissionsHelper
import com.bluetoothsniffer.repository.MacDescriptor
import com.bluetoothsniffer.utils.LocationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var permissionsHelper: PermissionsHelper

    @Inject
    lateinit var bluetoothUtils: BluetoothUtils

    @Inject
    lateinit var locationUtils: LocationUtils

    @Inject
    lateinit var listDevicesFactory: ListDevicesFactory

    @Inject
    lateinit var macDescriptor: MacDescriptor

    private lateinit var listDevices: ListDevices
    private var macVendorsJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as BluetoothSnifferApplication).appComponent.inject(this)
        updateDevicesCountLabel(0)

        scan_results_list.adapter = scanResultsAdapter
        scan_results_list.layoutManager = LinearLayoutManager(this)
        listDevices = ViewModelProviders.of(this, listDevicesFactory).get(ListDevices::class.java)

        with(listDevices) {
            results.observe(this@MainActivity, Observer { scanResults ->
                updateDevicesCountLabel(scanResults.size)
                scanResultsAdapter.swapData(scanResults)
                provideMacVendors()
            })
            error.observe(this@MainActivity, Observer { errorCode ->
                Toast.makeText(applicationContext,
                        getString(R.string.bluetooth_error, errorCode),
                        Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun provideMacVendors() {
        macVendorsJob?.cancel()
        macVendorsJob = GlobalScope.launch(Dispatchers.Main) {
            scanResultsAdapter.items.forEach { item ->
                val mac = item.scanResult.device.address
                val out = async(Dispatchers.IO) {
                    macDescriptor.resolveDeviceManufacturer(mac)
                }.await()
                item.macVendorName.set(out)
            }
        }
    }

    private fun updateDevicesCountLabel(count: Int) {
        found_devices_text.text = getString(R.string.found_devices, count)
    }

    override fun onResume() {
        super.onResume()
        handlePermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionsHelper.PERMISSIONS_REQUEST_CODE) {
            val anyDenied = grantResults.any { it == PackageManager.PERMISSION_DENIED }
            if (anyDenied) {
                Toast.makeText(this, R.string.permissions_are_required, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onDevicesAction(v: View) {
        listDevices.startStop()
        switchProgress()
        setButtonIcon()
    }

    private fun switchProgress() {
        discover_progress.visibility = if (discover_progress.visibility == View.INVISIBLE) View.VISIBLE else View.INVISIBLE
    }

    private fun setButtonIcon() {
        val icon = if (listDevices.isScanning) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play
        devices_action.setImageResource(icon)
    }

    private fun handleBluetooth() {
        when (bluetoothUtils.isBluetoothEnabled()) {
            false -> {
                Toast.makeText(this, R.string.bluetooth_is_off, Toast.LENGTH_SHORT).show()
                bluetoothUtils.forceEnableBluetooth(this)
                finish()
            }
            else -> checkLocation()
        }
    }

    private fun handlePermissions() {
        val hasPermissions = permissionsHelper.checkAllPermissionsGranted(this)
        when (hasPermissions) {
            false -> permissionsHelper.requestPermissions(this)
            else -> invokeFlow()
        }
    }

    private fun checkLocation() {
        if (locationUtils.isProviderDisabled()) {
            Toast.makeText(this, R.string.location_is_off, Toast.LENGTH_SHORT).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            finish()
        }
    }

    private fun invokeFlow() {
        handleBluetooth()
    }

    private val scanResultsAdapter by lazy { ScanResultsAdapter(this) }
}

package com.bluetoothsniffer.permissons

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.content.ContextCompat

class PermissionsHelper {

    companion object {
        val PERMISSIONS_REQUEST_CODE = 1000
        val REQUIRED_PERMISSIONS = arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    fun checkAllPermissionsGranted(activity: Activity) = REQUIRED_PERMISSIONS
            .all { ContextCompat.checkSelfPermission(activity, it) == PERMISSION_GRANTED }

    fun requestPermissions(activity: Activity) {
        activity.requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
    }
}
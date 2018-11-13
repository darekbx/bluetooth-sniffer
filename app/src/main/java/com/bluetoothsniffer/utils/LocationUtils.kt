package com.bluetoothsniffer.utils

import android.location.LocationManager

class LocationUtils(val locationManager: LocationManager) {

    fun isProviderDisabled() =
            !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}
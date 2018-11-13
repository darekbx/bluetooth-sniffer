package com.bluetoothsniffer.di

import android.bluetooth.BluetoothManager
import android.content.Context
import android.location.LocationManager
import com.bluetoothsniffer.BluetoothSnifferApplication
import com.bluetoothsniffer.bluetooth.BluetoothUtils
import com.bluetoothsniffer.permissons.PermissionsHelper
import com.bluetoothsniffer.utils.LocationUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: BluetoothSnifferApplication) {

    @Provides
    @Singleton
    fun provideApplication(): BluetoothSnifferApplication = application

    @Provides
    fun provideContext(): Context = application

    @Provides
    fun providePermissionsHelper() = PermissionsHelper()

    @Provides
    fun provideBluetoothUtils() = BluetoothUtils()

    @Provides
    fun provideBluetoothManager(context: Context) = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    @Provides
    fun provideLocationUtils(context: Context): LocationUtils {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationUtils(locationManager)
    }
}
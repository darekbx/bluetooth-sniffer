package com.bluetoothsniffer.di

import android.bluetooth.BluetoothManager
import android.content.Context
import android.location.LocationManager
import androidx.room.Room
import com.bluetoothsniffer.BluetoothSnifferApplication
import com.bluetoothsniffer.bluetooth.BluetoothUtils
import com.bluetoothsniffer.permissons.PermissionsHelper
import com.bluetoothsniffer.repository.local.AppDatabase
import com.bluetoothsniffer.utils.LocationUtils
import com.bluetoothsniffer.utils.MacShortener
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

    @Provides
    @Singleton
    fun provideMacShortener() = MacShortener()

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME).build()
}
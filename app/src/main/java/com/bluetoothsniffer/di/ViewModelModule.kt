package com.bluetoothsniffer.di

import android.bluetooth.BluetoothManager
import com.bluetoothsniffer.bluetooth.ListDevicesFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideListDevicesFactory(bluetoothManager: BluetoothManager)
        = ListDevicesFactory(bluetoothManager)
}
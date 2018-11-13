package com.bluetoothsniffer

import android.app.Application
import com.bluetoothsniffer.di.AppComponent
import com.bluetoothsniffer.di.AppModule
import com.bluetoothsniffer.di.DaggerAppComponent

class BluetoothSnifferApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}
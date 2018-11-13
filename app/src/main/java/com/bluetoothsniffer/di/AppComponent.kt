package com.bluetoothsniffer.di

import com.bluetoothsniffer.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ViewModelModule::class))
interface AppComponent {

    fun inject(activity: MainActivity)
}
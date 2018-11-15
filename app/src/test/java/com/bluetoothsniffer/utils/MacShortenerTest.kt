package com.bluetoothsniffer.utils

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class MacShortenerTest {

    @Test
    fun createShortMac() {
        val macShortener = MacShortener()
        assertNull(macShortener.createShortMac("FC:FB:FB:01"))
        assertEquals("FCFBFB", macShortener.createShortMac("FC:FB:FB:01:FA:21"))
    }
}
package com.bluetoothsniffer.repository

import com.bluetoothsniffer.utils.MacShortener

/*

Add Room and table caches_macs id | mac | name
mac is first six characters without colon like: FCFBFB

        curl -G "https://api.macvendors.com/v1/lookup/FC:FB:FB:01:FA:21" \
             -H "Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5c..."


*/
class MacVendorProvider {

    companion object {
        val AUTH_TOKEN = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJtYWN2ZW5kb3JzIiwiZXhwIjoxODU2NzI1NDQ0LCJpYXQiOjE1NDIyMjk0NDQsImlzcyI6Im1hY3ZlbmRvcnMiLCJqdGkiOiI3ZTFhNmRhNS0xYjkwLTQ1YjAtYjBmNS1jYmYyMzY1NTBjMTMiLCJuYmYiOjE1NDIyMjk0NDMsInN1YiI6IjkxMCIsInR5cCI6ImFjY2VzcyJ9.d91peGrdkB3NiMnUtF2n03xEaVXYqjtWBuacw02xqS2Mi4mbv-wI9IOU4oLjhcP4QNjsH9VLlBM38cbZnNHfXw"
        val API_URL = "https://api.macvendors.com/v1/lookup/"
    }

    fun requestVendorName(macAddress: String) {
        val macShorted = MacShortener().createShortMac(macAddress)


    }
}
package com.bluetoothsniffer.repository.remote

import com.bluetoothsniffer.model.MacVendorWrapper
import com.google.gson.Gson
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MacVendorProvider {

    companion object {
        val AUTH_TOKEN = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJtYWN2ZW5kb3JzIiwiZXhwIjoxODU2NzI1NDQ0LCJpYXQiOjE1NDIyMjk0NDQsImlzcyI6Im1hY3ZlbmRvcnMiLCJqdGkiOiI3ZTFhNmRhNS0xYjkwLTQ1YjAtYjBmNS1jYmYyMzY1NTBjMTMiLCJuYmYiOjE1NDIyMjk0NDMsInN1YiI6IjkxMCIsInR5cCI6ImFjY2VzcyJ9.d91peGrdkB3NiMnUtF2n03xEaVXYqjtWBuacw02xqS2Mi4mbv-wI9IOU4oLjhcP4QNjsH9VLlBM38cbZnNHfXw"
        val API_URL = "https://api.macvendors.com/v1/lookup/"
    }

    fun requestVendorName(macShorted: String): MacVendorWrapper? {
        val url = URL("$API_URL$macShorted")
        val urlConnection = (url.openConnection() as HttpsURLConnection).apply {
            setRequestProperty("Authorization", "Bearer $AUTH_TOKEN")
        }

        urlConnection.connect()
        urlConnection.inputStream.bufferedReader().use { buffer ->
            return Gson().fromJson(buffer.readText(), MacVendorWrapper::class.java)
        }
    }
}
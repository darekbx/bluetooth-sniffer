package com.bluetoothsniffer.repository.remote

import com.bluetoothsniffer.model.MacResponse
import com.bluetoothsniffer.model.MacVendorWrapper
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.net.HttpURLConnection

class MacVendorProvider {

    companion object {
        val AUTH_TOKEN = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJtYWN2ZW5kb3JzIiwiZXhwIjoxODU2NzI1NDQ0LCJpYXQiOjE1NDIyMjk0NDQsImlzcyI6Im1hY3ZlbmRvcnMiLCJqdGkiOiI3ZTFhNmRhNS0xYjkwLTQ1YjAtYjBmNS1jYmYyMzY1NTBjMTMiLCJuYmYiOjE1NDIyMjk0NDMsInN1YiI6IjkxMCIsInR5cCI6ImFjY2VzcyJ9.d91peGrdkB3NiMnUtF2n03xEaVXYqjtWBuacw02xqS2Mi4mbv-wI9IOU4oLjhcP4QNjsH9VLlBM38cbZnNHfXw"
        val API_URL = "https://api.macvendors.com/v1/lookup/"
    }

    enum class Status {
        FOUND,
        NOT_FOUND,
        ERROR
    }

    fun requestVendorName(macShorted: String): MacResponse {
        val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient()
                .newBuilder()
                .addInterceptor(loggingInterceptor)
                .build()
        val request = Request.Builder()
                .url("$API_URL$macShorted")
                .addHeader("Authorization", "Bearer $AUTH_TOKEN")
                .build()
        val response = client.newCall(request).execute()

        return when (response.code()) {
            HttpURLConnection.HTTP_OK -> {
                MacResponse(
                        wrapper = Gson().fromJson(response.body()?.string(), MacVendorWrapper::class.java),
                        status = Status.FOUND
                )
            }
            HttpURLConnection.HTTP_NOT_FOUND -> MacResponse(status = Status.NOT_FOUND)
            else -> MacResponse(status = Status.ERROR)
        }
    }
}
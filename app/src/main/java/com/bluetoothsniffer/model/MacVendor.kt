package com.bluetoothsniffer.model

import com.google.gson.annotations.SerializedName

class MacVendor(
        val assignment: String,
        @SerializedName("organization_address")
        val organizationAddress: String,
        @SerializedName("organization_name")
        val organizationName: String,
        val registry: String)
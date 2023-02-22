package com.rahman.bettary_app.network.responses

import com.google.gson.annotations.SerializedName
import com.rahman.bettary_app.network.model.BatteryDto

data class BatteryResponse (
    @SerializedName("data")
    var date: List<BatteryDto>)


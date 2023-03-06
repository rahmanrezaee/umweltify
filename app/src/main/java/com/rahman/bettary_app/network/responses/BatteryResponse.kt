package com.rahman.bettary_app.network.responses

import com.google.gson.annotations.SerializedName
import com.rahman.bettary_app.network.model.BatteryDto
import com.rahman.bettary_app.network.model.LoginDto

data class BatteryResponse(
    @SerializedName("data")
    var date: List<BatteryDto>)

data class LoginResponse (
    @SerializedName("user")
    var user: LoginDto,
    @SerializedName("accessToken")
    val accessToken:String,
    @SerializedName("refreshToken")
    val refreshToken:String
)

data class AddBatteryResponse (
    @SerializedName("data")
    var date: BatteryDto)

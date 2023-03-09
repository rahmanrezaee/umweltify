package com.rahman.umweltify.network.responses

import com.google.gson.annotations.SerializedName
import com.rahman.umweltify.network.model.BatteryDto
import com.rahman.umweltify.network.model.DashboardItemDto
import com.rahman.umweltify.network.model.LoginDto

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
data class DashboardResponse (
    @SerializedName("Data")
    var data: DashboardInnerResponse,
)

data class DashboardInnerResponse (
    @SerializedName("Data")
    var data: List<DashboardItemDto>,
    @SerializedName("Title")
    var title:String
)

data class AddBatteryResponse (
    @SerializedName("data")
    var date: BatteryDto)
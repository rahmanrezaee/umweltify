package com.rahman.umweltify.network.responses

import com.google.gson.annotations.SerializedName
import com.rahman.umweltify.network.model.BatteryDto
import com.rahman.umweltify.network.model.DashboardItemDto

data class BatteryResponse(
    @SerializedName("data")
    var date: List<BatteryDto>)

data class LoginResponse (
    @SerializedName("Data")
    var data:String,
    @SerializedName("Message")
    val message:String,
)
data class RegisterResponse (
    @SerializedName("Data")
    var data:String,
    @SerializedName("Message")
    val message:String,
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

package com.rahman.umweltify.network.model

import com.google.gson.annotations.SerializedName


data class DashboardItemDto(
    @SerializedName("DateTime")
    val date:String,
    @SerializedName("UnitNumber")
    val unitNumber:Int,
    @SerializedName("Value")
    val value:Double,
)
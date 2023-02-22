package com.rahman.bettary_app.network.model

import com.google.gson.annotations.SerializedName

data class BatteryDto(
    @SerializedName("name")
    val name:String? = null,

    @SerializedName("code")
    val code:String? = null,
)
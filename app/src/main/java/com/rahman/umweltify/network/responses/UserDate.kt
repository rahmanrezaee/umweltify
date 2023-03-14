package com.rahman.umweltify.network.responses

import com.google.gson.annotations.SerializedName



data class UserDate (
    @SerializedName("Id")
    var id:String,
    @SerializedName("Email")
    val email:String,
)
data class UserDateRegister (
    @SerializedName("userId")
    var id:String,
    @SerializedName("Email")
    val email:String,
)
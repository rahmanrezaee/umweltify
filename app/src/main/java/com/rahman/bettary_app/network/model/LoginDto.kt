package com.rahman.bettary_app.network.model

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("email")
    val email:String,
    @SerializedName("username")
    val username:String,

)
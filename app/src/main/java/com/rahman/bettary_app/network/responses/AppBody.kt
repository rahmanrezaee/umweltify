package com.rahman.bettary_app.network.responses

data class LoginRequestBody(val username: String,val password: String,val fcmToken: String = "fcmToken")



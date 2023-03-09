package com.rahman.umweltify.network.responses

data class LoginRequestBody(val email: String,val password: String)
data class RegisterRequestBody(val email: String,val password: String)

data class DashboardBodyModel(val from:String, val to:String)



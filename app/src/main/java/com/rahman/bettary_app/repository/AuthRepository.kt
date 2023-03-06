package com.rahman.bettary_app.repository

import com.rahman.bettary_app.network.responses.LoginResponse

interface AuthRepository {

    suspend fun login(email:String,password: String): Result<LoginResponse>
    suspend fun register(fullName:String,email:String,password: String)

}
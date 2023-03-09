package com.rahman.umweltify.repository

import com.rahman.umweltify.network.responses.LoginResponse

interface AuthRepository {

    suspend fun login(email:String,password: String): Result<LoginResponse>
    suspend fun register(fullName:String,email:String,password: String)

}
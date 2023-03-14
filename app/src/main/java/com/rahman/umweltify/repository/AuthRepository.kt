package com.rahman.umweltify.repository

import com.rahman.umweltify.domain.model.DeviceInfoModel
import com.rahman.umweltify.network.responses.DeviceResponse
import com.rahman.umweltify.network.responses.LoginResponse
import com.rahman.umweltify.network.responses.RegisterResponse

interface AuthRepository {

    suspend fun login(email:String,password: String): Result<LoginResponse>
    suspend fun addThingInfo(deviceInfo: DeviceInfoModel): Result<DeviceResponse>
    suspend fun register(email:String,password: String): Result<RegisterResponse>

}
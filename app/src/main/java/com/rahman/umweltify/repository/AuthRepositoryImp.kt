package com.rahman.umweltify.repository

import com.rahman.umweltify.domain.model.DeviceInfoModel
import com.rahman.umweltify.network.AppRequestService
import com.rahman.umweltify.network.responses.*
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor (
    private val requestService: AppRequestService,
): AuthRepository{
    override suspend fun login(email: String, password: String) : Result<LoginResponse> {
        return requestService.login(LoginRequestBody(email,password))
    }

    override suspend fun addThingInfo(deviceInfo: DeviceInfoModel): Result<DeviceResponse> {
        return requestService.addThingInfo(deviceInfo)
    }

    override suspend fun register( email: String, password: String):Result<RegisterResponse>  {
        return requestService.register(RegisterRequestBody(email,password))
    }

}
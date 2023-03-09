package com.rahman.umweltify.repository

import com.rahman.umweltify.network.AppRequestService
import com.rahman.umweltify.network.responses.LoginRequestBody
import com.rahman.umweltify.network.responses.LoginResponse
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor (
    private val requestService: AppRequestService,
): AuthRepository{
    override suspend fun login(email: String, password: String) : Result<LoginResponse> {

        return requestService.login(LoginRequestBody(email,password))

    }

    override suspend fun register(fullName: String, email: String, password: String) {
        TODO("Not yet implemented")
    }

}
package com.rahman.umweltify.network

import com.rahman.umweltify.domain.model.BatteryModel
import com.rahman.umweltify.network.responses.*
import retrofit2.http.Body
import retrofit2.http.POST


interface AppRequestService {


    @POST("Account/SignIn")
    suspend fun login(@Body loginRequest: LoginRequestBody):Result<LoginResponse>

    @POST("Account/SignUp")
    suspend fun register(@Body registerRequest: RegisterRequestBody):Result<RegisterResponse>

    @POST("consumption/addmeasurement")
    suspend fun insertBattery(@Body battery:BatteryModel) : Result<AddBatteryResponse>

    @POST("Dashboard/GetDeviceEmission")
    suspend fun getDashDeviceEmission(@Body body: DashboardBodyModel) : Result<DashboardResponse>

    @POST("Dashboard/GetUserLocationBasedEmission")
    suspend fun getDashUserLocationBasedEmission(@Body body: DashboardBodyModel) : Result<DashboardResponse>

    @POST("Dashboard/GetUserMarketBasedEmission")
    suspend fun getDashUserMarketBasedEmission(@Body body: DashboardBodyModel) : Result<DashboardResponse>

}
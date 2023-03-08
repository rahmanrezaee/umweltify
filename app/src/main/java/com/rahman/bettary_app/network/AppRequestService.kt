package com.rahman.bettary_app.network

import com.rahman.bettary_app.domain.model.BatteryModel
import com.rahman.bettary_app.domain.model.DashboardBodyModel
import com.rahman.bettary_app.network.responses.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AppRequestService {

    @GET("/")
    suspend fun getBatteryList(
//        @Header ("Authorization") toke :String,
//        @Query("page") page:Int,
//        @Query("limit") limit:Int,
    ):BatteryResponse


    @POST("/login")
    suspend fun login(
        @Body loginRequest: LoginRequestBody
    ):Result<LoginResponse>

    @POST("consumption/addmeasurement")
    suspend fun insertBattery(@Body battery:BatteryModel) : Result<AddBatteryResponse>

    @POST("Dashboard/GetDeviceEmission")
    suspend fun getDashboardData(@Body body: DashboardBodyModel) : Result<DashboardResponse>

}
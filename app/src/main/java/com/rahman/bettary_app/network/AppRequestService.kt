package com.rahman.bettary_app.network

import com.rahman.bettary_app.domain.model.BatteryModel
import com.rahman.bettary_app.network.responses.AddBatteryResponse
import com.rahman.bettary_app.network.responses.BatteryResponse
import com.rahman.bettary_app.network.responses.LoginRequestBody
import com.rahman.bettary_app.network.responses.LoginResponse
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

    @POST("/")
    suspend fun insertBattery(@Body battery:BatteryModel) : AddBatteryResponse

}
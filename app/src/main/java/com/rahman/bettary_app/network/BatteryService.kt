package com.rahman.bettary_app.network

import com.rahman.bettary_app.network.responses.BatteryResponse
import retrofit2.http.GET

interface BatteryService {

    @GET("language")
    suspend fun getBatteryList(
//        @Header ("Authorization") toke :String,
//        @Query("page") page:Int,
//        @Query("limit") limit:Int,

    ):BatteryResponse

}
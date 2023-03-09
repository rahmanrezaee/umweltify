package com.rahman.umweltify.repository

import com.rahman.umweltify.db.entity.BatteryED
import com.rahman.umweltify.domain.model.BatteryModel
import com.rahman.umweltify.network.responses.AddBatteryResponse
import com.rahman.umweltify.network.responses.DashboardResponse


interface BatteryRepository {

    suspend fun insertOne(battery: BatteryED)
    suspend fun getAll(isCharge:Boolean): List<BatteryED>


    suspend fun getGroup(): List<BatteryED>
    suspend fun getGroupForService(groupId:String): List<BatteryED>
    suspend fun getLastItem(): BatteryED

    suspend fun insertToServer(battery: BatteryModel) : Result<AddBatteryResponse>

    suspend fun getDashboardData():  Result<DashboardResponse>

}
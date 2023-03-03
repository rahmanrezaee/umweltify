package com.rahman.bettary_app.repository

import com.rahman.bettary_app.db.entity.BatteryED
import com.rahman.bettary_app.domain.model.BatteryModel
import com.rahman.bettary_app.network.responses.AddBatteryResponse


interface BatteryRepository {

    suspend fun insertOne(battery: BatteryED)
    suspend fun getAll(isCharge:Boolean): List<BatteryED>


    suspend fun getGroup(): List<BatteryED>

    suspend fun insertToServer(battery: BatteryModel) :  AddBatteryResponse



}
package com.rahman.bettary_app.repository

import com.rahman.bettary_app.db.entity.BatteryED


interface BatteryRepository {

    suspend fun insertOne(battery: BatteryED)
    suspend fun getAll(isCharge:Boolean): List<BatteryED>


    suspend fun getGroup(): List<BatteryED>

    suspend fun insertToServer(battery: BatteryED)
}
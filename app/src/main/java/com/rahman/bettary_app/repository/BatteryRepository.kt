package com.rahman.bettary_app.repository

import com.rahman.bettary_app.db.entity.BatteryED


interface BatteryRepository {

    suspend fun insertOne(battery: BatteryED)
    suspend fun getAll(): List<BatteryED>
}
package com.rahman.bettary_app.repository

import com.rahman.bettary_app.db.entity.BatteryED


interface BatteryRepository {


//    suspend fun getBattery(): List<BatteryModel>

    suspend fun insertOne(battery: BatteryED)

}
package com.rahman.bettary_app.domain.model

data class BatteryModel(
 val userId:String,
 val deviceId:String,
 val usageFrom:String,
 val usageTo:String,
 val averageMA:Double,
 val averageVoltage:Double,
 val totalWh:Double,
 val latitude:Double,
 val longitude:Double,
 val deviceManufacture:String,
 )

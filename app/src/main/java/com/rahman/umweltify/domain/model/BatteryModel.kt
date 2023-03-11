package com.rahman.umweltify.domain.model

data class BatteryModel(
 val userId:String,
 val deviceId:String,
 val from:String,
 val to:String,
 val averageAmpere:Double,
 val averageVoltage:Double,
 val totalWatts:Double,
 val latitude:Double,
 val longitude:Double,
// val deviceManufacture:String,
 )

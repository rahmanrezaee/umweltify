package com.rahman.bettary_app.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "battery_usage")
data class BatteryED(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "voltage") val voltage: String?,
    @ColumnInfo(name = "ampere") val ampere: String?,
    @ColumnInfo(name = "watt") val watt: String?,
    @ColumnInfo(name = "start_time") val startTime: String?,
    @ColumnInfo(name = "end_time") val endTime: String?,
)
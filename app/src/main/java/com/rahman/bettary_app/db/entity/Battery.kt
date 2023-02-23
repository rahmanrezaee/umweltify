package com.rahman.bettary_app.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "battery_usage")
data class BatteryED(
    // auto generate primary key
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo(name = "voltage") val voltage: Int?,
    @ColumnInfo(name = "ampere") val ampere: Int?,
    @ColumnInfo(name = "watt") val watt: Int?,
    @ColumnInfo(name = "is_charging") val isCharging: Boolean?,
    @ColumnInfo(name = "level") val level: Int?,
    @ColumnInfo(name = "start_time") val startTime: Long,
    @ColumnInfo(name = "end_time") val endTime: Long?,
)
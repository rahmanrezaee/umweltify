package com.rahman.umweltify.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "battery_usage")
data class BatteryED(
    // auto generate primary key
    @PrimaryKey(autoGenerate = true)
    val uid: Int? = null,
    @ColumnInfo(name = "voltage") val voltage: Int?,
    @ColumnInfo(name = "ampere") val ampere: Int?,
    @ColumnInfo(name = "source") val source: String?,
    @ColumnInfo(name = "watt") val watt: Double?,
    @ColumnInfo(name = "group_id") val group: String?,
    @ColumnInfo(name = "is_charging") val isCharging: Boolean?,
    @ColumnInfo(name = "level") val level: Int?,
    @ColumnInfo(name = "start_time") val startTime: Long,
    @ColumnInfo(name = "end_time") val endTime: Long?,
    @ColumnInfo(name = "address") val address: Int? = null,
)
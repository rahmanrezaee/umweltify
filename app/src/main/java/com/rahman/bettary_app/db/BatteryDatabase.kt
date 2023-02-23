package com.rahman.bettary_app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rahman.bettary_app.db.entity.BatteryED


@Database(
    entities = [BatteryED::class],
    version = 2,
    exportSchema = false
)

abstract class BatteryDatabase : RoomDatabase() {
    abstract fun getBatteryDao(): BatteryDao

    companion object {
        const val DB_NAME = "battery.db"
    }
}
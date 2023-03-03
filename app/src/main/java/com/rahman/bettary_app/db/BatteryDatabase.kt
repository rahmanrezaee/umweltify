package com.rahman.bettary_app.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.rahman.bettary_app.db.entity.AddressED
import com.rahman.bettary_app.db.entity.BatteryED


@Database(
    entities = [BatteryED::class,AddressED::class],
    version = 2,
    exportSchema = false,
)

abstract class BatteryDatabase : RoomDatabase() {
    abstract fun getBatteryDao(): BatteryDao

    abstract fun getAddressDao(): AddressDao
    companion object {
        const val DB_NAME = "battery.db"
    }
}
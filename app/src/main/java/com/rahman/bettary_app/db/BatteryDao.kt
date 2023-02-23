package com.rahman.bettary_app.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rahman.bettary_app.db.entity.BatteryED


@Dao
interface BatteryDao {
    @Query("SELECT * FROM battery_usage")
    fun getAll(): List<BatteryED>

    @Insert
    fun insert(item: BatteryED)

    @Delete
    fun delete(user: BatteryED)

}
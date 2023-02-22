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

    @Query("SELECT * FROM battery_usage WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<BatteryED>

    @Query(
        "SELECT * FROM battery_usage WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    fun findByName(first: String, last: String): BatteryED


    @Insert
    fun insert(item: BatteryED)

    @Delete
    fun delete(user: BatteryED)

}
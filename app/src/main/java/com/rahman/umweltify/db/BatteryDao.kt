package com.rahman.umweltify.db

import androidx.room.*
import com.rahman.umweltify.db.entity.BatteryED


@Dao
interface BatteryDao {

    // Battery Queries

    @Query("SELECT * FROM battery_usage WHERE is_charging = :isCharge ORDER BY start_time ASC LIMIT 1 ")
    fun getAll(isCharge:Boolean): List<BatteryED>

    @Query("SELECT * FROM battery_usage where uid in (SELECT max(uid) FROM battery_usage GROUP BY group_id ) order by uid desc limit 10")
    fun getGroup(): List<BatteryED>

    @Query("SELECT * FROM battery_usage WHERE group_id = :groupId")
    fun getGroupForService(groupId:String): List<BatteryED>
    @Query("SELECT * FROM battery_usage WHERE uid = ( SELECT MAX(uid) FROM battery_usage )")
    fun findLast(): BatteryED

    @Insert
    fun insert(item: BatteryED)

    @Delete
    fun delete(item: BatteryED)


}
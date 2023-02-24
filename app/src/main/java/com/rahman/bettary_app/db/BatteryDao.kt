package com.rahman.bettary_app.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rahman.bettary_app.db.entity.BatteryED


@Dao
interface BatteryDao {
    @Query("SELECT * FROM battery_usage WHERE is_charging = :isCharge ORDER BY start_time ASC LIMIT 1 ")
    fun getAll(isCharge:Boolean): List<BatteryED>

    @Query("SELECT * FROM battery_usage where uid in (SELECT max(uid) FROM battery_usage GROUP BY group_id ) order by uid desc limit 10")
    fun getGroup(): List<BatteryED>

//    "select date, sum(field1),( select field2 from your_table t2 where t.date = t2.date order by id desc limit 1 ) as field2 from table t group by date order by date"

    @Insert
    fun insert(item: BatteryED)

    @Delete
    fun delete(user: BatteryED)

}
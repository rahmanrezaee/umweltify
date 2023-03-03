package com.rahman.bettary_app.db
import androidx.room.*
import com.rahman.bettary_app.db.entity.AddressED
import com.rahman.bettary_app.db.entity.BatteryED


@Dao
interface AddressDao {


    // Address Queries
    @Query("SELECT * FROM address")
    fun getAll(): List<AddressED>

    @Insert
    fun insert(item: AddressED)


    @Update
    fun update(item: AddressED)

    @Delete
    fun delete(item: AddressED)



}
package com.rahman.bettary_app.db
import androidx.room.*
import com.rahman.bettary_app.db.entity.AddressED
import kotlinx.coroutines.flow.Flow


@Dao
interface AddressDao {

    // Address Queries
    @Query("SELECT * FROM address")
    fun getAll(): Flow< List<AddressED>>

    @Insert
    fun insert(item: AddressED)

    @Update
    fun update(item: AddressED)

    @Delete
    fun delete(item: AddressED)



}
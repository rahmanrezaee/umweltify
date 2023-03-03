package com.rahman.bettary_app.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressED (

    @PrimaryKey(autoGenerate = true)
    val uid:Int? = null,
    @ColumnInfo(name = "place_name")
    val placeName:String,
    @ColumnInfo
    val latitude:Double,
    @ColumnInfo
    val longitude :Double
)
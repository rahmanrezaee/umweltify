package com.rahman.bettary_app.repository

import com.rahman.bettary_app.db.entity.AddressED

interface AddressRepository {

    suspend fun getAll(): List<AddressED>

    suspend fun insertOne(item: AddressED)

    suspend fun deleteOne(item: AddressED)

    suspend fun updateOne(item: AddressED)


}
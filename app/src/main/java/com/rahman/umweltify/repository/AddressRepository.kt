package com.rahman.umweltify.repository

import com.rahman.umweltify.db.entity.AddressED
import kotlinx.coroutines.flow.Flow

interface AddressRepository {

    suspend fun getAll(): Flow<List<AddressED>>

    suspend fun insertOne(item: AddressED)

    suspend fun deleteOne(item: AddressED)

    suspend fun updateOne(item: AddressED)


}
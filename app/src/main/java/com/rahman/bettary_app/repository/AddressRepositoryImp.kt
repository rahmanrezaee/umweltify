package com.rahman.bettary_app.repository

import com.rahman.bettary_app.db.AddressDao
import com.rahman.bettary_app.db.entity.AddressED
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddressRepositoryImp @Inject constructor (
    private val dao: AddressDao,
): AddressRepository{
    override suspend fun getAll(): Flow<List<AddressED>> {
      return dao.getAll();
    }

    override suspend fun insertOne(item: AddressED){
        return dao.insert(item);
    }

    override suspend fun deleteOne(item: AddressED) {
        return dao.delete(item);
    }

    override suspend fun updateOne(item: AddressED) {
        return dao.update(item)
    }

}
package com.rahman.educationinfo.repository

import com.rahman.bettary_app.db.BatteryDao
import com.rahman.bettary_app.db.entity.BatteryED
import com.rahman.bettary_app.domain.model.BatteryModel
import com.rahman.bettary_app.network.BatteryService
import com.rahman.bettary_app.network.model.BatteryDtoMapper
import com.rahman.bettary_app.repository.BatteryRepository
import javax.inject.Inject

class BatteryRepositoryImp @Inject constructor (private val dao: BatteryDao) : BatteryRepository {
    override suspend fun insertOne(battery: BatteryED) {
        return dao.insert(battery)
    }

    override suspend fun getAll() : List<BatteryED> {
        return dao.getAll();
    }

}
package com.rahman.bettary_app.repository

import android.content.SharedPreferences
import com.rahman.bettary_app.R
import com.rahman.bettary_app.db.BatteryDao
import com.rahman.bettary_app.db.entity.BatteryED
import com.rahman.bettary_app.domain.model.BatteryModel
import com.rahman.bettary_app.network.AppRequestService
import com.rahman.bettary_app.network.responses.AddBatteryResponse
import com.rahman.bettary_app.persentation.BaseApplication
import javax.inject.Inject

class BatteryRepositoryImp @Inject constructor (
    private val dao: BatteryDao,
    private val context: BaseApplication,
    private val sharedPreferences: SharedPreferences,
    ) : BatteryRepository {


    @Inject
     lateinit var appRequestService: AppRequestService
    override suspend fun insertOne(battery: BatteryED) {

        val value:Int =  sharedPreferences.getInt(context.getString(R.string.address_key),-1);

        return dao.insert(if(value != -1)  battery.copy(address = value) else battery )
    }

    override suspend fun getAll(isCharge:Boolean) : List<BatteryED> {
        return dao.getAll(isCharge);
    }

    override suspend fun getGroup(): List<BatteryED> {
        return dao.getGroup();
    }

    override suspend fun insertToServer(battery: BatteryModel):  AddBatteryResponse  {
        return appRequestService.insertBattery(battery);
    }

}
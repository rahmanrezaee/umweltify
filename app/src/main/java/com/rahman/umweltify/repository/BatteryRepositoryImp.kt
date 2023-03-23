package com.rahman.umweltify.repository

import android.content.SharedPreferences
import com.rahman.umweltify.R
import com.rahman.umweltify.db.BatteryDao
import com.rahman.umweltify.db.entity.BatteryED
import com.rahman.umweltify.domain.model.BatteryModel
import com.rahman.umweltify.network.AppRequestService
import com.rahman.umweltify.network.responses.AddBatteryResponse
import com.rahman.umweltify.network.responses.DashboardBodyModel
import com.rahman.umweltify.network.responses.DashboardResponse
import com.rahman.umweltify.presentation.BaseApplication
import javax.inject.Inject

class BatteryRepositoryImp @Inject constructor(
    private val dao: BatteryDao,
    private val context: BaseApplication,
    private val sharedPreferences: SharedPreferences,

    ) : BatteryRepository {

    @Inject
    lateinit var appRequestService: AppRequestService
    override suspend fun insertOne(battery: BatteryED) {

        val value: Int = sharedPreferences.getInt(context.getString(R.string.address_key), -1);

        return dao.insert(if (value != -1) battery.copy(address = value) else battery)
    }

    override suspend fun getAll(isCharge: Boolean): List<BatteryED> {
        return dao.getAll(isCharge);
    }

    override suspend fun getGroup(): List<BatteryED> {
        return dao.getGroup();
    }

    override suspend fun getGroupForService(groupId: String): List<BatteryED> {
        return dao.getGroupForService(groupId)
    }

    override suspend fun getLastItem(): BatteryED {
        return dao.findLast()
    }

    override suspend fun insertToServer(battery: BatteryModel): Result<AddBatteryResponse> {
        return appRequestService.insertBattery(battery);
    }

    override suspend fun getDashboardData(): Result<DashboardResponse> {

        return appRequestService.getDashUserMarketBasedEmission(
            DashboardBodyModel(
                "2023-03-06T10:50:58.635Z",
                "2023-03-06T10:50:58.635Z"
            )
        )
    }

    override suspend fun getDashboardDataDevice(): Result<DashboardResponse> {

        return appRequestService.getDashDeviceEmission(
            DashboardBodyModel(
                "2023-03-06T10:50:58.635Z",
                "2023-03-06T10:50:58.635Z"
            )
        )
    }
    override suspend fun getDashboardDataLocation(): Result<DashboardResponse> {

        return appRequestService.getDashUserLocationBasedEmission(
            DashboardBodyModel("2023-03-06T10:50:58.635Z", "2023-03-06T10:50:58.635Z")
        )
    }
}
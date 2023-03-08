package com.rahman.bettary_app.persentation.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.os.BatteryManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahman.bettary_app.db.entity.BatteryED
import com.rahman.bettary_app.network.responses.DashboardResponse
import com.rahman.bettary_app.persentation.BaseApplication
import com.rahman.bettary_app.persentation.service.*
import com.rahman.bettary_app.persentation.util.RequestState
import com.rahman.bettary_app.repository.BatteryRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@SuppressLint("CheckResult")
@HiltViewModel
class BatteryChargingViewModel @Inject constructor(
    private val application: BaseApplication,
    private val repository: BatteryRepositoryImp
) : ViewModel() {

    var chargeState: MutableState<BatteryStateBroadCast?> = mutableStateOf(null);
    var batteryInfo: MutableState<BatteryChangeData?> = mutableStateOf(null);
    var historyBatteryItems: MutableState<List<BatteryED>> = mutableStateOf(listOf());

    val dashboardItems =
        MutableStateFlow<RequestState<DashboardResponse>>(RequestState.Idle)


    private var batteryManager: BatteryManager =
        application.applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager;

    init {
        getChargerInfo()
        getDashboardData()
    }

    fun getDashboardData(refreshInductor: Boolean = false) {
        if (refreshInductor) {
            dashboardItems.value = RequestState.LoadingRefresh
        }else{
            dashboardItems.value = RequestState.Loading
        }
        try {
            viewModelScope.launch {
                // Trigger the flow and consume its elements using collect
                repository.getDashboardData().onSuccess {
                    dashboardItems.value = RequestState.Success(it)
                }.onFailure {
                    dashboardItems.value = RequestState.Error(it)
                }
            }
        } catch (e: Exception) {
            dashboardItems.value = RequestState.Error(e)
        }


    }

    private fun getChargerInfo() {


        snapshotFlow {
            chargeState.value?.isCharging
        }.onEach {
            Log.i("BatteryReceiver", "snapshotFlow Changed")
//            items.value = repository.getGroup();
            syncDataOfData(result = repository.getGroup())

        }.launchIn(viewModelScope)


        BatteryReceiver.observe(application).subscribeOn(Schedulers.io()).subscribe {
            chargeState.value = it;
            Log.i("BatteryReceiver", "Charging Changed")
        }

        Observable.interval(5, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).subscribe {

            batteryInfo.value = BatteryChangeData(
                averageAmpere = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE),
                currentAmpere = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW),
                batteryCapacity = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER),
                remainingEnergy = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER)
            );

        }


    }

    private fun syncDataOfData(result: List<BatteryED>) {
        // check getNexValue
        if (result.isNotEmpty() && historyBatteryItems.value.isNotEmpty() && result.first().group == historyBatteryItems.value.first().group) {
            viewModelScope.launch {
                delay(1000L)
                syncDataOfData(repository.getGroup())
            }
        } else {
            historyBatteryItems.value = result;
        }
    }

}


data class BatteryChangeData(
    val currentAmpere: Int,
    val averageAmpere: Int,
    val batteryCapacity: Int,
    val remainingEnergy: Int
)

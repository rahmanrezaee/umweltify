package com.rahman.bettary_app.persentation.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.os.BatteryManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahman.bettary_app.db.entity.BatteryED
import com.rahman.bettary_app.persentation.BaseApplication
import com.rahman.bettary_app.persentation.service.BatteryReceiver
import com.rahman.bettary_app.repository.BatteryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@SuppressLint("CheckResult")
@HiltViewModel
class BatteryChargingViewModel @Inject constructor(
    private val application: BaseApplication,
    private val repository: BatteryRepository

) : ViewModel() {

    var isCharging : MutableState<Boolean> =  mutableStateOf(false);
    var currentVoltage : MutableState<Int> =  mutableStateOf(0);
    var voltage : MutableState<Int> =  mutableStateOf(0);

    var items : MutableState<List<BatteryED>> = mutableStateOf(listOf());

    private var batteryManager: BatteryManager = application.applicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager;

    init {

        snapshotFlow {
            isCharging.value
        }.onEach {
            Log.i("BatteryReceiver","snapshotFlow Changed")
//            items.value = repository.getGroup();
            syncDataOfData(result = repository.getGroup())

        }.launchIn(viewModelScope)


        BatteryReceiver.observe(application).subscribeOn(Schedulers.io()).subscribe {


            isCharging.value = it;
            Log.i("BatteryReceiver","Charging Changed")
        }

        Observable.interval(5, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).subscribe {
            Log.i("voltage","voltage value  ${currentVoltage.value }")
            currentVoltage.value = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
            voltage.value = batteryManager.getIntProperty(BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE);
            viewModelScope.launch {

            }
        }



    }

    private fun syncDataOfData( result:List<BatteryED>) {
        // check getNexValue
        if(result.isNotEmpty() && items.value.isNotEmpty() && result.first().group == items.value.first().group ){
            viewModelScope.launch {
                delay(1000L)
                syncDataOfData(repository.getGroup())
            }
        }else{
            items.value = result;
        }
    }

}
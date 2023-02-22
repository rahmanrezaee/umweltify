package com.rahman.bettary_app.persentation.contentProvider


import android.annotation.SuppressLint
import android.content.Context
import android.os.BatteryManager
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class RxBatteryManager {

    companion object {
        fun observe(context: Context): Observable<BatteryManagerState> {
            var observable: Observable<BatteryManagerState> =
                Observable.interval(20, TimeUnit.SECONDS).flatMap {
                    return@flatMap Observable.create { shooter ->

                        val batteryManager: BatteryManager =
                            context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager;
                        val batteryLevel =
                            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
                        val batteryCapacity =
                            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
                        val currentAverage =
                            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE)
                        val currentNow =
                            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)
                        val remainingEnergy =
                            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER)

                        shooter.onNext(
                            BatteryManagerState(
                                batteryLevel = batteryLevel,
                                batteryCapacity = batteryCapacity,
                                currentAverage = currentAverage,
                                currentNow = currentNow,
                                remainingEnergy = remainingEnergy
                            ),
                        )

                        shooter.onComplete()
                        // Action to be done when completed 10 secs
                    }
                }.subscribeOn(Schedulers.io())

            return observable
        }
    }

}

data class BatteryManagerState(
    val batteryLevel: Int,
    val remainingEnergy: Int,
    val currentNow: Int,
    val currentAverage: Int,
    val batteryCapacity: Int
)

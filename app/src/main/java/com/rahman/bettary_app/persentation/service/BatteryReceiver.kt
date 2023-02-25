package com.rahman.bettary_app.persentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableEmitter


class BatteryReceiver {

    companion object {
        const val UNKNOWN = -1
        @JvmStatic
        fun observe(context: Context): Flowable<BatteryStateBroadCast> {
            var receiver: BroadcastReceiver? = null
            return Flowable.create({ emitter ->
                receiver = createBroadcastReceiver(emitter)
                context.registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            }, BackpressureStrategy.BUFFER)
                .doOnCancel { context.unregisterReceiver(receiver) }
        }
        private fun createBroadcastReceiver(emitter: FlowableEmitter<BatteryStateBroadCast>): BroadcastReceiver {
            return object : BroadcastReceiver() {
                override fun onReceive(
                    context: Context?,
                    intent: Intent?
                ) {
                    if (intent == null) {
                        return
                    }


                    val status: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, UNKNOWN)
                    val health: Int = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, UNKNOWN)
                    val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, UNKNOWN)
                    val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, UNKNOWN)
                    val technology: String = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY) ?: ""
                    val temperature: Int =
                        intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, UNKNOWN)

                    val voltage: Int = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, UNKNOWN)

                    val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                            || status == BatteryManager.BATTERY_STATUS_FULL



                    emitter.onNext(
                        BatteryStateBroadCast(
                            isCharging,
                            health,
                            level,
                            scale,
                            temperature,
                            voltage,
                            technology
                        )
                    )


                }
            }
        }
    }
}


data class BatteryStateBroadCast(
    val isCharging: Boolean,
    val healthCode: Int,
    val level: Int,
    val scale: Int,
    val temperature: Int,
    val voltage: Int,
    val technology: String?
) {

    fun health(): Health {
        return when (healthCode) {
            BatteryManager.BATTERY_HEALTH_COLD -> Health.COLD
            BatteryManager.BATTERY_HEALTH_DEAD -> Health.DEAD
            BatteryManager.BATTERY_HEALTH_GOOD -> Health.GOOD
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> Health.OVERHEAT
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> Health.OVER_VOLTAGE
            BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> Health.UNSPECIFIED_FAILURE
            else -> Health.UNKNOWN
        }
    }
}

enum class Health {
    COLD,
    DEAD,
    GOOD,
    OVERHEAT,
    OVER_VOLTAGE,
    UNKNOWN,
    UNSPECIFIED_FAILURE
}


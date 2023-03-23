package com.rahman.umweltify.presentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
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
                    val plugged: Int = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, UNKNOWN)


                    val voltage: Int = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, UNKNOWN)

                    val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                            || status == BatteryManager.BATTERY_STATUS_FULL



                    emitter.onNext(
                        BatteryStateBroadCast(
                            isCharging,
                            status,
                            plugged,
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
    val statusCode: Int,
    val pluggedCode: Int,
    val healthCode: Int,
    val level: Int,
    val scale: Int,
    val temperature: Int,
    val voltage: Int,
    val technology: String?
) {

    fun status(): Status {
        return when (statusCode) {
            BatteryManager.BATTERY_STATUS_CHARGING -> Status.CHARGING
            BatteryManager.BATTERY_STATUS_DISCHARGING -> Status.DISCHARGING
            BatteryManager.BATTERY_STATUS_FULL -> Status.FULL
            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> Status.NOT_CHARGING
            else -> Status.UNKNOWN
        }
    }

    fun plugged(): Plugged {
        return when (pluggedCode) {
            BatteryManager.BATTERY_PLUGGED_AC -> Plugged.AC
            BatteryManager.BATTERY_PLUGGED_USB -> Plugged.USB
            BatteryManager.BATTERY_PLUGGED_WIRELESS -> Plugged.WIRELESS
            else -> Plugged.UNKNOWN
        }
    }

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



enum class Plugged {
    AC,
    USB,
    WIRELESS,
    UNKNOWN
}

enum class Status {
    CHARGING,
    DISCHARGING,
    FULL,
    NOT_CHARGING,
    UNKNOWN
}


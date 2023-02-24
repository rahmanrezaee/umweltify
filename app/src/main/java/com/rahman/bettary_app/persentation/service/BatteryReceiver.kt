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
        fun observe(context: Context): Flowable<Boolean> {
            var receiver: BroadcastReceiver? = null
            return Flowable.create({ emitter ->
                receiver = createBroadcastReceiver(emitter)
                context.registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            }, BackpressureStrategy.BUFFER)
                .doOnCancel { context.unregisterReceiver(receiver) }
        }
        private fun createBroadcastReceiver(emitter: FlowableEmitter<Boolean>): BroadcastReceiver {
            return object : BroadcastReceiver() {
                override fun onReceive(
                    context: Context?,
                    intent: Intent?
                ) {
                    if (intent == null) {
                        return
                    }
                    val status: Int = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, UNKNOWN) ?: UNKNOWN
                    val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                            || status == BatteryManager.BATTERY_STATUS_FULL

                    emitter.onNext(
                        isCharging
                    )
                }
            }
        }
    }
}

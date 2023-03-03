package com.rahman.bettary_app.persentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Bundle
import android.widget.Toast
import com.rahman.bettary_app.persentation.AddressActivity


object BroadCastConst{
    const val UNKNOWN = -1
    const val addressIntent:String = "address.intent.broadcast"
}

abstract class AddressActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val bundle = intent.getIntExtra("address",-1)
        broadcastResult(bundle)

    }

    protected abstract  fun broadcastResult(address: Int)

}

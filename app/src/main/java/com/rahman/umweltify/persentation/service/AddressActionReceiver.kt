package com.rahman.umweltify.persentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


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

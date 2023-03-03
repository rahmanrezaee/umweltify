package com.rahman.bettary_app.persentation


import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import com.rahman.bettary_app.R
import com.rahman.bettary_app.persentation.service.BroadCastConst
import com.rahman.bettary_app.persentation.theme.BatteryTheme
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class AddressActivity : ComponentActivity() {


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            showOnLockScreenAndTurnScreenOn()
        }

        setContent {
            BatteryTheme(
                darkTheme = false
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    Arrangement.Center,
                    Alignment.CenterHorizontally
                ) {
                    Text(text = "Select Address Activity")
                    Button(onClick = {

                        var addressBroadCast = Intent();
                        addressBroadCast.action = BroadCastConst.addressIntent
                        addressBroadCast.putExtra("address", 102)
                        sendBroadcast(addressBroadCast);

//                        finish()
                    }) {
                        Text(text = "Select")
                    }

                    var percentage by remember {
                        mutableStateOf(0f)
                    }

                    AndroidView({ context->

                        RiveAnimationView(context).also { rive ->
                            rive.setRiveResource(
                                resId = R.raw.charging,
                                stateMachineName = "State Machine",
                            )
                            Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).subscribe {
                                percentage += it;
                                rive.setNumberState("State Machine","Load Percentage",percentage)
                            }
                        }

                    })

                }

            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O_MR1)
    private fun showOnLockScreenAndTurnScreenOn() {
        setShowWhenLocked(true)
        setTurnScreenOn(true)

        var key: KeyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager;
        key.requestDismissKeyguard(this, null)

        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )

        val notifManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notifManager.cancel(123)

    }


}
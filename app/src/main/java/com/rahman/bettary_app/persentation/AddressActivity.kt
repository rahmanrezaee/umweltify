package com.rahman.bettary_app.persentation


import android.app.KeyguardManager
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rahman.bettary_app.persentation.theme.Bettary_appTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddressActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            showOnLockScreenAndTurnScreenOn()
        }

        setContent {
            Bettary_appTheme(
                darkTheme = false
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    Arrangement.Center,
                    Alignment.CenterHorizontally
                ) {
                    Text(text = "Select Address Activity")
                    Button(onClick = {
                        finish()
                    }) {
                        Text(text = "Select")
                    }
                }

            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O_MR1)
    private fun showOnLockScreenAndTurnScreenOn() {
        setShowWhenLocked(true)
        setTurnScreenOn(true)

        var key :KeyguardManager= getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager;
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
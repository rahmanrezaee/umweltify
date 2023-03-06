package com.rahman.bettary_app.persentation


import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import com.rahman.bettary_app.R
import com.rahman.bettary_app.persentation.screens.randomBackground
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
                    Modifier.fillMaxSize()
                        .padding(top = 20.dp),
                    Arrangement.Center,
                    Alignment.Start
                ) {

                    Text(
                        text = "Charging Address",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                    Text(
                        text = "Please Select One of Address Which Current You Do Charge!",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                    ElevatedCard(
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = Color.White,
                        ),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 20.dp
                        ),
                        shape = RoundedCornerShape(5),
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .fillMaxWidth()
                            .clickable {
                                finish()
                            }

                    ) {
                        Box(
                            Modifier
                                .randomBackground()
                                .padding(start = 5.dp)
                                .background(Color.White)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Text(
                                    text = "Home", modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )

                            }
                        }

                    }
                    ElevatedCard(
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = Color.White,
                        ),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 20.dp
                        ),
                        shape = RoundedCornerShape(5),
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .fillMaxWidth()
                            .clickable {
                                finish()
                            }

                    ) {
                        Box(
                            Modifier
                                .randomBackground()
                                .padding(start = 5.dp)
                                .background(Color.White)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Text(
                                    text = "Office", modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )

                            }
                        }

                    }

                    ElevatedCard(
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = Color.White,
                        ),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 20.dp
                        ),
                        shape = RoundedCornerShape(5),
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .fillMaxWidth()
                            .clickable {
                                finish()
                            }

                    ) {
                        Box(
                            Modifier
                                .randomBackground()
                                .padding(start = 5.dp)
                                .background(Color.White)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Text(
                                    text = "Other", modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )

                            }
                        }

                    }
                    var percentage by remember {
                        mutableStateOf(0f)
                    }
                    AndroidView({ context ->

                        RiveAnimationView(context).also { rive ->
                            rive.setRiveResource(
                                resId = R.raw.charging,
                                stateMachineName = "State Machine",
                            )
                            Observable.interval(5, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                                .subscribe {
                                    percentage += it;
                                    rive.setNumberState(
                                        "State Machine",
                                        "Load Percentage",
                                        percentage
                                    )
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
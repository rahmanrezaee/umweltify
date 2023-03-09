package com.rahman.umweltify.persentation


import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rahman.umweltify.R
import com.rahman.umweltify.db.entity.AddressED
import com.rahman.umweltify.persentation.theme.BatteryTheme
import com.rahman.umweltify.persentation.theme.Typography
import com.rahman.umweltify.persentation.util.RequestState
import com.rahman.umweltify.persentation.viewModel.AddressViewModel
import com.rahman.umweltify.persentation.viewModel.BatteryChargingViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddressActivity : ComponentActivity() {


    private val batteryCharging: BatteryChargingViewModel by viewModels()
    private val addressVM: AddressViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("CheckResult", "UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            showOnLockScreenAndTurnScreenOn()
        }
        setContent {
            BatteryTheme(
                darkTheme = false,
            ) {
                Column(
                    Modifier.clip(RoundedCornerShape(10.dp)).padding(10.dp),
                    Arrangement.Center,
                    Alignment.Start
                ) {

                    TopAppBar(
                        title = {
                            Text(
                                text = "Select Address",
                                maxLines = 1,
                                style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                    Text(
                        text = "Please Select One of Address Which Current You Do Charge!",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                    val allItems by addressVM.items.collectAsState()
                    if (allItems is RequestState.Success) {
                        var innerItem: List<AddressED> =
                            (allItems as RequestState.Success<List<AddressED>>).data
                        for (item in innerItem) {
                            ListItem(
                                modifier = Modifier.clickable {
                                    addressVM.selectAddress(item)
                                    finish()
                                },
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.round_my_location),
                                        contentDescription = null
                                    )
                                },
                                supportingText = {
                                    Text(
                                        text = "(${String.format("%.1f",item.latitude)},${String.format("%.1f",item.longitude)})",
                                    )
                                },
                                headlineText = {
                                    Text(
                                        text = item.placeName,
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.round_check_circle),
                                        tint = if (addressVM.selectedAddress.value == item.placeName) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.scrim,
                                        contentDescription = null
                                    )

                                }
                            )
                        }
                    }
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
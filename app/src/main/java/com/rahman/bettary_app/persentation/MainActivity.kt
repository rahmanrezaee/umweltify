package com.rahman.bettary_app.persentation

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.rahman.bettary_app.persentation.routes.MainNav
import com.rahman.bettary_app.persentation.service.BatteryService
import com.rahman.bettary_app.persentation.theme.Bettary_appTheme
import com.rahman.bettary_app.persentation.viewModel.BatteryViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    private val viewModel: BatteryViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBatteryService()
        setContent {
            Bettary_appTheme(
                darkTheme = false
            )  {

                MainNav()

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupBatteryService() {

        Log.i("BaseApplication", "setupBatteryService: Start Service");
        if (isMyServiceRunning(BatteryService::class.java)) {
            stopService(Intent(this, BatteryService::class.java))
            Toast.makeText(this, "Service is running", Toast.LENGTH_SHORT).show();
        } else {
            startForegroundService(Intent(this, BatteryService::class.java))
            Toast.makeText(this, "Service is not running", Toast.LENGTH_SHORT).show();
        }

    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}

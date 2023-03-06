package com.rahman.bettary_app.persentation

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.*
import com.rahman.bettary_app.persentation.components.general.RequestPermissionComponent
import com.rahman.bettary_app.persentation.components.general.customRememberPermissionState
import com.rahman.bettary_app.persentation.components.general.showAlertSetting
import com.rahman.bettary_app.persentation.routes.MainNav
import com.rahman.bettary_app.persentation.service.BatteryService
import com.rahman.bettary_app.persentation.theme.BatteryTheme
import com.rahman.bettary_app.persentation.viewModel.SetupViewModel
import com.rahman.bettary_app.persentation.viewModel.ThemeState
import dagger.hilt.android.AndroidEntryPoint


@OptIn(ExperimentalPermissionsApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val setupViewModel: SetupViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        setContent {
            BatteryTheme(
                darkTheme = when(setupViewModel.themeState.value){
                    ThemeState.LIGHT_MODE -> {
                        false
                    }
                    ThemeState.SYSTEM ->{
                        isSystemInDarkTheme()
                    }
                    ThemeState.DARK_MODE -> {
                        true
                    }
                }
            ) {
                var context = LocalContext.current

                if (Build.VERSION.SDK_INT >= 33) {

                    val pushPermissionState = customRememberPermissionState(
                        permission = Manifest.permission.POST_NOTIFICATIONS,
                        onCannotRequestPermission = {
                            context.showAlertSetting(
                                "Notification Permission",
                                "Notification",
                                context.packageName
                            )
                        }
                    )
                    if (pushPermissionState.status.isGranted) {
                        mainView(setupViewModel)
                    } else {
                        RequestPermissionComponent(title ="You haven't Notification Permission") {
                            pushPermissionState.launchPermissionRequest()
                        }
                    }
                } else {
                    mainView(setupViewModel)
                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
   fun mainView(setupViewModel:SetupViewModel){
        setupBatteryService()
        MainNav(setupViewModel)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupBatteryService() {


//
//        val intent = Intent(this, HeadsUpNotificationService::class.java)
//        startForegroundService(intent)

        Log.i("BaseApplication", "setupBatteryService: Start Service");
        if (!isMyServiceRunning(BatteryService::class.java)) {
            startForegroundService(Intent(this, BatteryService::class.java))
            Toast.makeText(this, "Service is not running", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Service is running no need to stop", Toast.LENGTH_SHORT).show();
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

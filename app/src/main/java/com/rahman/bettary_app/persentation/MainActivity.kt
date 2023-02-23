package com.rahman.bettary_app.persentation

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rahman.bettary_app.persentation.routes.MainNav
import com.rahman.bettary_app.persentation.routes.Routes
import com.rahman.bettary_app.persentation.service.BatteryService
import com.rahman.bettary_app.persentation.theme.Bettary_appTheme
import com.rahman.bettary_app.persentation.viewModel.BatteryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@OptIn(ExperimentalPermissionsApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: BatteryViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Bettary_appTheme(
                darkTheme = false
            ) {
                if (Build.VERSION.SDK_INT >= 33) {

                    val notificationPermissionState = rememberPermissionState(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                    if (notificationPermissionState.status.isGranted) {
                        setupBatteryService()
                        MainApp()
                    } else {
                        RequestNotificationPage(this,notificationPermissionState)
                    }
                } else {
                    setupBatteryService()
                    MainApp()
                }
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

@Composable
fun MainApp() {
    MainNav()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPage(context: Context,notificationPermissionState: PermissionState) {

        Column(Modifier.fillMaxSize()) {

            Text(text = "Not Allow To Notification")

            if (notificationPermissionState.status.shouldShowRationale) {
                Text(text = "Allow The Notifications")
                notificationPermissionState.launchPermissionRequest()
            } else {

                Button(onClick = {
                    // Open app settings enable permission
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:com.rahman.bettary_app")
                    context.startActivity(intent)
                }) {
                    Text(text = "Go Setting And Enable Notification")
                }

            }
        }

}

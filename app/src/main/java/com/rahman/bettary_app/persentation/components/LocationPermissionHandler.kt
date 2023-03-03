package com.rahman.bettary_app.persentation.components

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.*


@SuppressLint("PermissionLaunchedDuringComposition")
@ExperimentalPermissionsApi
@Composable
fun LocationPermissionHandler(
    content: @Composable () -> Unit,
) {

    val context = LocalContext.current

    val permissionRequestState = rememberRequestPermissionsState(
        permissions = Manifest.permission.ACCESS_FINE_LOCATION
    )

    var locationState by remember {
        mutableStateOf(LocationPermissionState.Denied)
    }


    val permissionState =
        rememberPermissionState(permission = permissionRequestState.permission) { isGranted ->
            // This block will be triggered after the user chooses to grant or deny the permission
            // and we can tell if the user permanently declines or if we need to show rational
            val permissionPermanentlyDenied = !ActivityCompat.shouldShowRequestPermissionRationale(
                context.findActivity(), permissionRequestState.permission
            ) && !isGranted

            if (permissionPermanentlyDenied) {
                locationState = LocationPermissionState.PERMANENT_DENIED
            } else if (!isGranted) {
                locationState = LocationPermissionState.Denied
            }
        }


    if (permissionRequestState.requestPermission) {
        permissionRequestState.requestPermission = false
        if (permissionState.status.isGranted) {
            locationState = LocationPermissionState.GRAND
        } else {
            locationState = LocationPermissionState.Denied

        }
    }


    PermissionContent(permissionState, locationState, content) {
        if (locationState == LocationPermissionState.PERMANENT_DENIED) {
            LocationAlertSetting(context)
        } else if (locationState == LocationPermissionState.Denied) {
            permissionState.launchPermissionRequest()
        }
    }


}


@ExperimentalPermissionsApi
@Composable
fun PermissionContent(
    permission: PermissionState,
    locationState: LocationPermissionState,
    content: @Composable () -> Unit,
    onRetry: () -> Unit
) {

    when (locationState) {
        LocationPermissionState.PERMANENT_DENIED -> {
            LocationAlertSetting(LocalContext.current)
            RetryGetLocation {
                onRetry.invoke()
            }
        }
        LocationPermissionState.Denied -> {
            LaunchedEffect(key1 = Unit) {
                permission.launchPermissionRequest()
            }
            RetryGetLocation {
                onRetry.invoke()
            }
        }
        LocationPermissionState.GRAND ->
            content()
    }


}

@Composable
fun RetryGetLocation(onRetry: () -> Unit){
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Button(onClick = { onRetry.invoke() }) {
            Text(text = "Get Location Permission")
        }
    }

}

fun LocationAlertSetting(context: Context) {

    val builder1 = AlertDialog.Builder(context)
    builder1.setTitle("Location Permission Request")
    builder1.setMessage("You must turn off from Go To \nSetting > Permissions > Location and Allow location Permission")
    builder1.setCancelable(true)

    builder1.setPositiveButton(
        "Go Setting"
    ) { dialog, id ->

        context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        })
        dialog.cancel()

    }
    builder1.setNegativeButton(
        "Cancel"
    ) { dialog, id -> dialog.cancel() }

    val alert11 = builder1.create()
    alert11.show()

}


/**
 * request permission or not
 */
class RequestPermissionState(initRequest: Boolean, val permission: String) {
    var requestPermission by mutableStateOf(initRequest)
}

/**
 * Remember whether permission should be requested or not, true initially by default
 */
@Composable
fun rememberRequestPermissionsState(
    initRequest: Boolean = true,
    permissions: String
): RequestPermissionState {
    return remember {
        RequestPermissionState(initRequest, permissions)
    }
}

/**
 * Permission requester
 */
@OptIn(ExperimentalPermissionsApi::class)
fun RequestPermission(
    context: Context,
    requestState: RequestPermissionState,
    permissionState: PermissionState,
    granted: () -> Unit,
    showRational: () -> Unit,
    permanentlyDenied: () -> Unit
) {


    // If requestPermission, then launchPermissionRequest and the user will be able to choose
    // to grant or deny the permission.
    // After that, the RequestPermission will recompose and permissionState above will be triggered
    // and we can differentiate whether the permission is permanently declined or whether rational
    // should be shown


}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}


enum class LocationPermissionState {
    GRAND,
    Denied,
    PERMANENT_DENIED
}

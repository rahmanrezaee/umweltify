package com.rahman.umweltify.persentation.components.general

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
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
import com.rahman.umweltify.persentation.util.findActivity


internal fun Activity.shouldShowRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}


@ExperimentalPermissionsApi
@Composable
fun customRememberPermissionState(
    permission: String,
    onCannotRequestPermission: () -> Unit = {},
    onPermissionResult: (Boolean) -> Unit = {},
): ExtendedPermissionState {
    val context = LocalContext.current

    var currentShouldShowRationale by remember {
        mutableStateOf(context.findActivity().shouldShowRationale(permission))
    }

    var atDoubleDenialForPermission by remember {
        mutableStateOf(false)
    }

    val mutablePermissionState = rememberPermissionState(permission) { isGranted ->
        if (!isGranted) {
            val updatedShouldShowRationale = context.findActivity().shouldShowRationale(permission)
            if (!currentShouldShowRationale && !updatedShouldShowRationale)
                onCannotRequestPermission()
            else if (currentShouldShowRationale && !updatedShouldShowRationale)
                atDoubleDenialForPermission = false
        }
        onPermissionResult(isGranted)
    }

    return remember(permission) {
        ExtendedPermissionState(
            permission = permission,
            mutablePermissionState = mutablePermissionState,
            onCannotRequestPermission = onCannotRequestPermission,
            atDoubleDenial = atDoubleDenialForPermission,
            onLaunchedPermissionRequest = {
                currentShouldShowRationale = it
            }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Stable
class ExtendedPermissionState(
    override val permission: String,
    private val mutablePermissionState: PermissionState,
    private val atDoubleDenial: Boolean,
    private val onLaunchedPermissionRequest: (shouldShowRationale: Boolean) -> Unit,
    private val onCannotRequestPermission: () -> Unit
) : PermissionState {
    override val status: PermissionStatus
        get() = mutablePermissionState.status

    override fun launchPermissionRequest() {
        onLaunchedPermissionRequest(mutablePermissionState.status.shouldShowRationale)
        if (atDoubleDenial) onCannotRequestPermission()
        else mutablePermissionState.launchPermissionRequest()
    }
}



fun Context.showAlertSetting(title: String,permissionName:String,packageName:String) {

    val builder1 = AlertDialog.Builder(this)
    builder1.setTitle(title)
    builder1.setMessage("You must turn On from Setting Page \ngo To Setting > Permissions > $permissionName and Allow $permissionName Permission")
    builder1.setCancelable(true)

    builder1.setPositiveButton(
        "Go Setting"
    ) { dialog, id ->

        this.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package",packageName, null)
        })
        dialog.cancel()

    }
    builder1.setNegativeButton(
        "Cancel"
    ) { dialog, id -> dialog.cancel() }

    val alert11 = builder1.create()
    alert11.show()

}


@Composable
fun RequestPermissionComponent(title:String,onRetryClick: () -> Unit) {

    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {

        Text(text = title)
        Button(onClick = {
            onRetryClick.invoke()
        }) {
            Text(text = "Enable")
        }
    }
}





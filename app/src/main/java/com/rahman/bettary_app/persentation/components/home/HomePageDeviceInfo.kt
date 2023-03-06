package com.rahman.bettary_app.persentation.components.home

import android.os.Build
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun HomeDeviceInfo() {
    //
    Text(text = "Release ${Build.VERSION.RELEASE}")
    Text(text = "Sdk ${Build.VERSION.SDK_INT}")
    Text(text = "Os ${Build.VERSION.BASE_OS}")
    Text(text = "Model ${Build.MODEL}")
    Text(text = "Manufacturer ${Build.MANUFACTURER}")
    Text(text = "A30s ${Build.DEVICE}")
    Text(text = "a30sdx ${Build.PRODUCT}")
    Text(text = "Arc ${Build.CPU_ABI}")
    Text(text = "Hardware ${Build.HARDWARE}")
}

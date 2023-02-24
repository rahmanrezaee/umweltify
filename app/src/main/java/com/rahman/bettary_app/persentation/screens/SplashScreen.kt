@file:OptIn(ExperimentalPermissionsApi::class)

package com.rahman.bettary_app.persentation.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.rahman.bettary_app.R
import com.rahman.bettary_app.persentation.routes.Routes
import com.rahman.bettary_app.persentation.service.BatteryService
import kotlinx.coroutines.delay


@Preview
@SuppressLint("PermissionLaunchedDuringComposition")
@Composable
fun SplashScreen(nav: NavController = NavController(LocalContext.current)) {


    var context = LocalContext.current;
    LaunchedEffect(key1 = true ){
        delay(500L)

            nav.navigate(Routes.Dashboard.name)

    }
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .width(100.dp)
        )


    }


}


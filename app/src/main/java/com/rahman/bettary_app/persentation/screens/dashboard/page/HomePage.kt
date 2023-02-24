@file:OptIn(ExperimentalPermissionsApi::class)

package com.rahman.bettary_app.persentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.rahman.bettary_app.persentation.components.HomeChargingInfo
import com.rahman.bettary_app.persentation.components.HomeDeviceInfo
import com.rahman.bettary_app.persentation.components.HomeGraph
import com.rahman.bettary_app.persentation.viewModel.BatteryViewModel

@Preview
@SuppressLint("CheckResult")
@Composable
fun HomePage() {
    val batteryViewModel: BatteryViewModel = hiltViewModel()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)

    ) {
        item {
            HomeGraph()
        }
        item {

            HomeChargingInfo()
        }

        item {

            HomeDeviceInfo()
        }
    }
}


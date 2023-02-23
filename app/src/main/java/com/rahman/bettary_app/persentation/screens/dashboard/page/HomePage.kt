@file:OptIn(ExperimentalPermissionsApi::class)

package com.rahman.bettary_app.persentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.rahman.bettary_app.persentation.viewModel.BatteryViewModel

@SuppressLint("CheckResult")
@Composable
fun HomePage(

) {
    val batteryViewModel: BatteryViewModel = hiltViewModel()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(batteryViewModel.items.value.size) {
           Row( modifier = Modifier
               .fillMaxWidth()

           ) {
               Text(text = "${batteryViewModel.items.value[it].uid} ", style = MaterialTheme.typography.h5)
               Column(
                     modifier = Modifier
                          .weight(1f)
               ) {

                   Row() {
                       Text(text = "${batteryViewModel.items.value[it].ampere} Am - ")
                       Text(text = batteryViewModel.items.value[it].watt.toString())
                   }
                   Row() {
                       Text(text = "${batteryViewModel.items.value[it].startTime} - ")
                       Text(text = batteryViewModel.items.value[it].endTime.toString())
                   }
               }
           }
        }
    }
//    Column(
//        modifier = androidx.compose.ui.Modifier
//            .fillMaxSize()
//
//    ) {

//        // Camera permission state
//        val notificationPermissionState = rememberPermissionState(
//            Manifest.permission.POST_NOTIFICATIONS
//        )
//
//        // LineGraph
//        val style2 = LineGraphStyle(
//            visibility = LinearGraphVisibility(
//                isHeaderVisible = true,
//                isYAxisLabelVisible = true,
//                isGridVisible = true,
//                isXAxisLabelVisible = true,
//                isCrossHairVisible = true,
//            ),
////            colors = LinearGraphColors(
////                lineColor = Color.Green,
//////                pointColor = GraphAccent2,
//////                clickHighlightColor = PointHighlight2,
//////                fillGradient = Brush.verticalGradient(
//////                    listOf(Gradient3, Gradient2)
//////                )
////            )
//        )
//
//        LineGraph(
//            style =style2,
//            xAxisData = listOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat").map {
//                GraphData.String(it)
//            }, // xAxisData : List<GraphData>, and GraphData accepts both Number and String types
//            yAxisData = listOf(200, 40, 60, 450, 700, 30, 50),
//        )
//
//        if (notificationPermissionState.status.isGranted) {
//            Text("Notification permission Granted")
//        } else {
//            Column {
//                val textToShow =
//                    if (notificationPermissionState.status.shouldShowRationale) {
//                        "The Notification is important for this app. Please grant the permission."
//                    } else {
//                        "You have permanently denied the Notification permission. Please enable it from the settings."
//                    }
//
//
//                Text(textToShow)
//                Button(onClick = {
//                    if (notificationPermissionState.status.shouldShowRationale) {
//                        notificationPermissionState.launchPermissionRequest()
//                    } else {
//                        // Open app settings enable permission
//                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                        intent.data = Uri.parse("package:com.rahman.bettary_app")
//                        context.startActivity(intent)
//                    }
//                }) {
//                    Text("Request permission")
//                }
//
//            }
//        }
////
////        Text(text = "Release ${android.os.Build.VERSION.RELEASE}")
////        Text(text = "Sdk ${android.os.Build.VERSION.SDK_INT}")
////        Text(text = "Os ${android.os.Build.VERSION.BASE_OS}")
////        Text(text = "Model ${android.os.Build.MODEL}")
////        Text(text = "Manufacturer ${android.os.Build.MANUFACTURER}")
//        Text(text = "A30s ${android.os.Build.DEVICE}")
//        Text(text = "a30sdx ${android.os.Build.PRODUCT}")
//        Text(text = "Arc ${android.os.Build.CPU_ABI}")
//        Text(text = "Hardware ${android.os.Build.HARDWARE}")



}
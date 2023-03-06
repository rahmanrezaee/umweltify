package com.rahman.bettary_app.persentation.screens.dashboard.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rahman.bettary_app.persentation.components.home.HomeChargingInfo
import com.rahman.bettary_app.persentation.components.home.HomeChargingInfoCharging
import com.rahman.bettary_app.persentation.components.home.HomeGraph
import com.rahman.bettary_app.persentation.components.home.MainChargingContent
import com.rahman.bettary_app.persentation.routes.Routes
import com.rahman.bettary_app.persentation.theme.Typography
import com.rahman.bettary_app.persentation.viewModel.BatteryChargingViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CheckResult", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(mainNav: NavController) {


    var batteryCharging: BatteryChargingViewModel = hiltViewModel()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Dashboard",
                        maxLines = 1,
                        style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = {
                        mainNav.navigate(Routes.SettingScreen.name)
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            modifier = Modifier.size(30.dp),
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 0.dp, horizontal = 10.dp)

        ) {

            item{
                Spacer(modifier = Modifier.height(70.dp))
            }
            item {
                HomeGraph()
            }
            item {
                MainChargingContent(batteryCharging)
            }
            item {
                HomeChargingInfo()
            }
            item {
                HomeChargingInfoCharging()
            }

            item{
                Spacer(modifier = Modifier.height(70.dp))
            }

        }

    }

}



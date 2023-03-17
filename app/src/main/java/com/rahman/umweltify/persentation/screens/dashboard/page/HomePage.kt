package com.rahman.umweltify.persentation.screens.dashboard.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.fengdai.compose.pulltorefresh.PullToRefresh
import com.github.fengdai.compose.pulltorefresh.rememberPullToRefreshState
import com.rahman.umweltify.R
import com.rahman.umweltify.persentation.components.home.*
import com.rahman.umweltify.persentation.routes.Routes
import com.rahman.umweltify.persentation.theme.Typography
import com.rahman.umweltify.persentation.util.RequestState
import com.rahman.umweltify.persentation.viewModel.BatteryChargingViewModel
import com.rahman.umweltify.persentation.viewModel.SetupViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CheckResult", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(mainNav: NavController, setupViewModel: SetupViewModel,batteryCharging:BatteryChargingViewModel) {

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
//                        mainNav.navigate(Routes.SettingScreen.name)
                        mainNav.navigate(Routes.ProfileScreen.name)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.user_outline),
                            modifier = Modifier.size(30.dp),
                            contentDescription = ""
                        )
                    }
                }
            )
        },
    ) {
        val allItems by batteryCharging.dashboardItems.collectAsState()
        PullToRefresh(
            state = rememberPullToRefreshState(isRefreshing = allItems is RequestState.LoadingRefresh),
            onRefresh = {
                batteryCharging.getDashboardData(true)
            },
            dragMultiplier = 1f,
            modifier = Modifier.padding(top = 70.dp),
            refreshTriggerDistance = 100.dp,
            refreshingOffset = 60.dp,
        ) {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 0.dp, horizontal = 10.dp)

            ) {
                item {
                    HomeGraph(batteryCharging)
                }
//                item {
//                    HomeChargingInfoCharging(batteryCharging)
//                }
                item {
                    MainChargingContent(batteryCharging, setupViewModel)
                }
//                item {
////                    HomeChargingInfo(batteryCharging)
//                }
                item {
                    LastChargeTime(
                        batteryCharging
                    )
                }
//                item {
//                    Spacer(modifier = Modifier.height(70.dp))
//                }
            }
        }
    }
}




package com.rahman.umweltify.persentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.*
import com.rahman.umweltify.persentation.components.bottom_navigation.BasicBottomNav
import com.rahman.umweltify.persentation.routes.NavigationGraph
import com.rahman.umweltify.persentation.viewModel.AuthViewModel
import com.rahman.umweltify.persentation.viewModel.BatteryChargingViewModel
import com.rahman.umweltify.persentation.viewModel.SetupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CheckResult",
    "UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition"
)
@Composable
fun Dashboard(nav: NavHostController,authViewModel : AuthViewModel,setupViewModel: SetupViewModel,batterVM: BatteryChargingViewModel) {

    Log.i("loginState","loginState ${authViewModel.loginState.value}")
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BasicBottomNav(navController,nav,authViewModel)
        },
        contentWindowInsets = WindowInsets.statusBars,
        
        content = {
            NavigationGraph(navController = navController, mainNav = nav, authViewModel = authViewModel,setupViewModel,batterVM)
        }
    )
}

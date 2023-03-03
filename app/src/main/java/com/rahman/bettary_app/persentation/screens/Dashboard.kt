

package com.rahman.bettary_app.persentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.*
import com.rahman.bettary_app.persentation.components.bottom_navigation.BasicBottonNav
import com.rahman.bettary_app.persentation.routes.NavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CheckResult",
    "UnusedMaterial3ScaffoldPaddingParameter"
)
@Composable
fun Dashboard(nav: NavHostController) {

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BasicBottonNav(navController)
        },
        contentWindowInsets = WindowInsets.statusBars,
        
        content = {
            NavigationGraph(navController = navController, mainNav = nav)
        }
    )
}

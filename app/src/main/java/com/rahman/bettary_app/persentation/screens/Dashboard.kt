@file:OptIn(ExperimentalPermissionsApi::class)

package com.rahman.bettary_app.persentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.*
import com.rahman.bettary_app.persentation.components.bottom_navigation.BasicBottonNav
import com.rahman.bettary_app.persentation.routes.NavigationGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CheckResult")
@Composable
fun Dashboard(nav: NavController) {




    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Home Page")
                }
            )
        },
        bottomBar = {
            BasicBottonNav(navController)
        },
        content = {
            NavigationGraph(navController = navController)
        }
    )
}
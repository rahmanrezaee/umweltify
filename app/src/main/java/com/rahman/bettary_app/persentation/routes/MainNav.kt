package com.rahman.bettary_app.persentation.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rahman.bettary_app.persentation.screens.*
import com.rahman.materialp.component.bottom_navigation.BottomNavItem

@Composable
fun MainNav() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen.name ){
        composable(Routes.HomeScreen.name){
            Dashboard(nav = navController)
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomePage()
        }
        composable(BottomNavItem.Action.screen_route) {
            ActionPage()
        }
        composable(BottomNavItem.Shop.screen_route) {
            ShopPage()
        }
        composable(BottomNavItem.Profile.screen_route) {
            ProfilePage()
        }
    }
}
package com.rahman.bettary_app.persentation.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rahman.bettary_app.persentation.components.bottom_navigation.BottomNavItem
import com.rahman.bettary_app.persentation.screens.*
import com.rahman.bettary_app.persentation.screens.dashboard.page.ActionPage
import com.rahman.bettary_app.persentation.screens.dashboard.page.HomePage
import com.rahman.bettary_app.persentation.screens.dashboard.page.ProfilePage
import com.rahman.bettary_app.persentation.screens.dashboard.page.ShopPage

@Composable
fun MainNav() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.name ){
        composable(Routes.Dashboard.name){
            Dashboard(nav = navController)
        }
        composable(Routes.AddressScreen.name){
            AddressScreen(nav = navController)
        }
        composable(Routes.SplashScreen.name){
                SplashScreen(nav = navController)
        }
        composable(Routes.OnBoardScreen.name){
                OnboardScreen(nav = navController)
        }
        composable(Routes.LoginScreen.name){
                LoginScreen(nav = navController)
        }
        composable(Routes.RegisterScreen.name){
            RegisterScreen(nav = navController)
        }
        composable(Routes.ForgetPasswordScreen.name){
            ForgetPassword(nav = navController)
        }
        composable(Routes.ResetPasswordScreen.name){
            ResetPasswordScreen(nav = navController)
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController,mainNav:NavHostController) {
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
            ProfilePage(mainNav)
        }
    }
}
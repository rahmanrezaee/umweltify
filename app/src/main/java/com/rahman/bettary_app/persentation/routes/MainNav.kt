package com.rahman.bettary_app.persentation.routes

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
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
import com.rahman.bettary_app.persentation.viewModel.AuthViewModel
import com.rahman.bettary_app.persentation.viewModel.SetupViewModel

@Composable
fun MainNav(setupViewModel: SetupViewModel) {

    val authViewModel: AuthViewModel = hiltViewModel()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.name ){
        composable(Routes.Dashboard.name){
            Dashboard(nav = navController,authViewModel)
        }
        composable(Routes.AddressScreen.name){
            AddressScreen(nav = navController)
        }
        composable(Routes.SplashScreen.name){
                SplashScreen(nav = navController,authViewModel,setupViewModel)
        }
        composable(Routes.SettingScreen.name){
            SettingScreen(nav = navController,setupViewModel)
        }
        composable(Routes.OnBoardScreen.name){
                OnboardScreen(nav = navController,setupViewModel)
        }
        composable(Routes.LoginScreen.name){
                LoginScreen(nav = navController,authViewModel)
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

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NavigationGraph(navController: NavHostController,mainNav:NavHostController, authViewModel: AuthViewModel) {




    NavHost(
        navController,
        startDestination = BottomNavItem.Home.screen_route
    ) {
        composable(BottomNavItem.Home.screen_route,) {
            HomePage(mainNav)
        }
        composable(BottomNavItem.Action.screen_route) {
            ActionPage()
        }
        composable(BottomNavItem.Shop.screen_route) {
            ShopPage()
        }
        composable(BottomNavItem.Profile.screen_route) {
            ProfilePage(mainNav,authViewModel)
        }
    }
}


//extensions
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry?.viewModel(): T? = this?.let {
    viewModel(viewModelStoreOwner = it)
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.viewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
): T {
    return androidx.lifecycle.viewmodel.compose.viewModel(
        viewModelStoreOwner = viewModelStoreOwner, key = T::class.java.name
    )
}
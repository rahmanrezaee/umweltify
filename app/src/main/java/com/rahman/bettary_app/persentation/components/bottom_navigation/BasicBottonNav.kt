package com.rahman.bettary_app.persentation.components.bottom_navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rahman.bettary_app.persentation.routes.Routes
import com.rahman.bettary_app.persentation.viewModel.AuthViewModel
import com.rahman.bettary_app.persentation.viewModel.LoginState


@Composable
fun BasicBottomNav(navController: NavController, mainNav: NavController,authViewModel: AuthViewModel) {



    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Action,
        BottomNavItem.Shop,
        BottomNavItem.Profile,
    )
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier
            .clip(RoundedCornerShape(topEndPercent = 50, topStartPercent = 50))
            .shadow(10.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .height(60.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(
                            id =
                            if (currentRoute == item.screen_route)
                                item.activeIcon
                            else item.defaultIcon
                        ),
                        modifier = Modifier.size(25.dp),
                        contentDescription = item.title
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
//                    selectedTextColor = Color.Transparent,
//                    selectedIconColor = Color.Transparent
                ),
                selected = currentRoute == item.screen_route,
//                modifier = Modifier.padding(0.dp),
                onClick = {


                    Log.i("loginstate","loginState ${authViewModel.loginState.value}")
                    if (item.needLogin) {
                        if (authViewModel.loginState.value != LoginState.AUTHORIZED) {
                            mainNav.navigate(Routes.LoginScreen.name)
                            return@NavigationBarItem
                        }
                    }
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}



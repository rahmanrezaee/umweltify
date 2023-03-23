package com.rahman.umweltify.presentation.components.bottom_navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rahman.umweltify.presentation.routes.Routes
import com.rahman.umweltify.presentation.viewModel.AuthViewModel
import com.rahman.umweltify.presentation.viewModel.LoginState


@Composable
fun BasicBottomNav(
    navController: NavController,
    mainNav: NavController,
    authViewModel: AuthViewModel
) {


    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Action,
        BottomNavItem.Shop,
        BottomNavItem.Profile,
    )
    NavigationBar(
        containerColor = Color.Transparent,
        windowInsets = WindowInsets(0.dp),
        modifier = Modifier
            .shadow(10.dp)
            .padding(0.dp)
            .height(60.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer)

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->

            NavigationBarItem(

                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painterResource(
                                id =
                                if (currentRoute == item.screen_route)
                                    item.activeIcon
                                else item.defaultIcon
                            ),
                            modifier = Modifier.size(20.dp),
                            contentDescription = item.title
                        )
                        Text(text = item.title, style = MaterialTheme.typography.labelSmall.copy(fontWeight =
                            if (currentRoute == item.screen_route)
                                FontWeight.SemiBold
                            else FontWeight.Light
                        ))
                    }
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
//                    selectedTextColor = Color.Transparent,
//                    selectedIconColor = Color.Transparent
                ),

                selected = currentRoute == item.screen_route,
                modifier = Modifier.padding(0.dp),
                onClick = {


                    Log.i("loginstate", "loginState ${authViewModel.loginState.value}")
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



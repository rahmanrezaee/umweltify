package com.rahman.bettary_app.persentation.components.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import com.rahman.bettary_app.R

sealed class BottomNavItem(var title:String, var activeIcon:Int, var defaultIcon:Int, var screen_route:String, var badge:String = ""){

    object Home : BottomNavItem("Home",  R.drawable.home_fill, R.drawable.home_outline, "home",)
    object Action: BottomNavItem("Action",R.drawable.chart_fill,R.drawable.chart_outline,"action")
    object Shop: BottomNavItem("Market",R.drawable.store_fill,R.drawable.store_outline,"shop", )
    object Profile: BottomNavItem("Profile",R.drawable.user_fill,R.drawable.user_outline,"profile")

}
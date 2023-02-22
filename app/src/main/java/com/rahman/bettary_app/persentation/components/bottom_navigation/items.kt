package com.rahman.materialp.component.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title:String, var icon:ImageVector, var screen_route:String, var badge:String = ""){

    object Home : BottomNavItem("Home", Icons.Default.Home,"home",)
    object Action: BottomNavItem("Action",Icons.Default.MailOutline,"action")
    object Shop: BottomNavItem("Market",Icons.Default.ShoppingCart,"shop", )
    object Profile: BottomNavItem("Profile",Icons.Default.Person,"profile")

}
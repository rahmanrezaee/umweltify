package com.rahman.bettary_app.persentation.components.bottom_navigation

import com.rahman.bettary_app.R

sealed class BottomNavItem(var title:String,
                           var activeIcon:Int,
                           var defaultIcon:Int,
                           var screen_route:String,
                           var needLogin:Boolean
){

    object Home : BottomNavItem("Home",  R.drawable.home_fill, R.drawable.home_outline, "home",needLogin = false)
    object Action: BottomNavItem("Action",R.drawable.chart_fill,R.drawable.chart_outline,"action",needLogin = false)
    object Shop: BottomNavItem("Market",R.drawable.store_fill,R.drawable.store_outline,"shop", needLogin = false)
    object Profile: BottomNavItem("Profile",R.drawable.user_fill,R.drawable.user_outline,"profile",needLogin = false)

}
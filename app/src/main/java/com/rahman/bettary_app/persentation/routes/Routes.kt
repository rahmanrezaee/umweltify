package com.rahman.bettary_app.persentation.routes

enum class Routes{
    SplashScreen,
    LoginScreen,
    RegisterScreen,
    Dashboard,
    DetailScreen;

    companion object {
        fun fromRoute(route: String?): Routes =
            when(route?.substringBefore("/")){
                SplashScreen.name -> SplashScreen
                LoginScreen.name -> LoginScreen
                RegisterScreen.name -> RegisterScreen
                Dashboard.name -> Dashboard
                DetailScreen.name -> DetailScreen
                null -> Dashboard
                else -> throw IllegalArgumentException("Route $route Not Found")
            }
    }

}
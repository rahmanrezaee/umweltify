package com.rahman.bettary_app.persentation.routes

enum class Routes{
    SplashScreen,
    LoginScreen,
    RegisterScreen,
    HomeScreen,
    DetailScreen;

    companion object {
        fun fromRoute(route: String?): Routes =
            when(route?.substringBefore("/")){
                SplashScreen.name -> SplashScreen
                LoginScreen.name -> LoginScreen
                RegisterScreen.name -> RegisterScreen
                HomeScreen.name -> HomeScreen
                DetailScreen.name -> DetailScreen
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route $route Not Found")
            }
    }

}
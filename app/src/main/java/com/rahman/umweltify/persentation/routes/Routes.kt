package com.rahman.umweltify.persentation.routes

enum class Routes{
    SplashScreen,
    LoginScreen,
    RegisterScreen,
    ResetPasswordScreen,
    ForgetPasswordScreen,
    Dashboard,
    AddressScreen,
    ProfileScreen,
    OnBoardScreen,
    SettingScreen,
    DetailScreen;

    companion object {
        fun fromRoute(route: String?): Routes =
            when(route?.substringBefore("/")){
                SplashScreen.name -> SplashScreen
                LoginScreen.name -> LoginScreen
                RegisterScreen.name -> RegisterScreen
                Dashboard.name -> Dashboard
                AddressScreen.name -> AddressScreen
                ProfileScreen.name -> ProfileScreen
                SettingScreen.name -> SettingScreen
                OnBoardScreen.name -> OnBoardScreen
                ForgetPasswordScreen.name -> ForgetPasswordScreen
                ResetPasswordScreen.name -> ResetPasswordScreen
                DetailScreen.name -> DetailScreen
                null -> Dashboard
                else -> throw IllegalArgumentException("Route $route Not Found")
            }
    }

}
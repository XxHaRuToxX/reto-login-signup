package com.xxharutoxx.loginapp.navegation

open class ScreensManager(val route: String) {
    object LoginScreen: ScreensManager(route = "login_screen")
    object RegisterScreen: ScreensManager(route = "register_screen")
    object HomeScreen: ScreensManager(route = "home_screen")
}
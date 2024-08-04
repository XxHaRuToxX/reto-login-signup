package com.xxharutoxx.loginapp.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xxharutoxx.loginapp.ui.screens.HomeScreen
import com.xxharutoxx.loginapp.ui.screens.LoginScreen
import com.xxharutoxx.loginapp.ui.screens.PreviewScreen
import com.xxharutoxx.loginapp.viewmodels.UserViewModel

@Composable
fun Navigation(){
    val viewModel = UserViewModel()
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = ScreensManager.LoginScreen.route ){
        composable(route = ScreensManager.LoginScreen.route){
            LoginScreen(userViewModel = viewModel, navController = navController)
        }
        composable(route = ScreensManager.RegisterScreen.route){
            PreviewScreen(userViewModel = viewModel, navController = navController)
        }
        composable(route = ScreensManager.HomeScreen.route){
            HomeScreen()
        }
    }
}
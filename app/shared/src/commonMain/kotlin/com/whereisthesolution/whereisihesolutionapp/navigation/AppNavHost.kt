package com.whereisthesolution.whereisihesolutionapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home.HomeScreen
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login.LoginScreen

@Composable
fun AppNavHost(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable(route = "login"){
            LoginScreen(
                onLoginClick = {
                    navController.navigate("home")
                }
            )
        }
        composable(route = "home"){
            HomeScreen()
        }
    }
}
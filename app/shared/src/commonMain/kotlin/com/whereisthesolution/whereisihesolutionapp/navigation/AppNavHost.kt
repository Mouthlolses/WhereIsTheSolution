package com.whereisthesolution.whereisihesolutionapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereisthesolution.whereisihesolutionapp.network.dto.RegisterUserRequest
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home.HomeScreen
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home.HomeViewModel
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login.LoginScreen
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable(route = "login") {

            val viewmodel: LoginViewModel = koinViewModel()

            LoginScreen(
                onLoginClick = {
                    navController.navigate("home")
                },
                onNavigationToHome = { navController.navigate("home") },
                onRegisterUser = { name, city, email, password ->
                    viewmodel.registerUser(
                        userRequest = RegisterUserRequest(
                            name = name,
                            email = email,
                            password = password
                        )
                    )
                },
                uiEvent = viewmodel.uiEvent,
            )
        }
        composable(route = "home") {

            val viewmodel: HomeViewModel = koinViewModel()
            val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = uiState
            )
        }
    }
}
package com.whereisthesolution.whereisihesolutionapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereisthesolution.whereisihesolutionapp.network.dto.AuthUserRequest
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
            val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

            LoginScreen(
                onLoginClick = {
                    viewmodel.loginUser(
                        userRequest = AuthUserRequest(
                            uiState.email,
                            uiState.password
                        )
                    )
                },
                onNavigationToHome = {
                    navController
                        .navigate("home") {
                            popUpTo("login") {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                },
                onRegisterUser = {
                    viewmodel.registerUser(
                        userRequest = RegisterUserRequest(
                            name = uiState.name,
                            email = uiState.email,
                            password = uiState.password
                        )
                    )
                },
                uiEvent = viewmodel.uiEvent,
                uiState = uiState,
                name = viewmodel::onNameChanged,
                email = viewmodel::onEmailChanged,
                password = viewmodel::onPasswordChanged,
            )
        }
        composable(route = "home") {

            val viewmodel: HomeViewModel = koinViewModel()
            val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = uiState,
                uiEvent = viewmodel.uiEvent,
                onNavigationToLogin = {
                    navController
                        .navigate("login") {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                },
                logout = viewmodel::logout
            )
        }
    }
}
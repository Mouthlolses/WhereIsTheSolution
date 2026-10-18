package com.whereisthesolution.whereisihesolutionapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home.HomeScreen
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home.HomeViewModel
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login.LoginScreenContainer
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.login.LoginViewModel
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.registerOccurrence.RegisterOccurrenceScreen
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

            LoginScreenContainer(
                viewmodel = viewmodel,
                uiState = uiState,
                navController = navController
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
                onNavigationToRegisterPost = {
                    navController.navigate("register-post")
                },
                logout = viewmodel::logout
            )
        }
        composable(route = "register-post") {
            RegisterOccurrenceScreen(
                onBackScreen = {
                    navController.popBackStack()
                }
            )
        }
    }
}
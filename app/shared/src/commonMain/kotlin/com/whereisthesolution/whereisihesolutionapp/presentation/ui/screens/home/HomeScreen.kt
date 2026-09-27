package com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.components.CustomModalNavigationDrawer
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.components.CustomTopAppBar
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.components.FeedCity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState(),
    uiEvent: Flow<HomeUiEvent> = flowOf(),
    onNavigationToLogin: () -> Unit = {},
    logout: () -> Unit = {},
) {

    val user = uiState.user

    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val snackBarState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        uiEvent.collect { event ->

            when (event) {
                HomeUiEvent.NavigateToLogin -> {
                    onNavigationToLogin()
                }

                is HomeUiEvent.ShowError -> {
                    snackBarState.showSnackbar("Error")
                }
            }
        }
    }

    if (uiState.loading) {
        CircularProgressIndicator()
    } else {
        CustomModalNavigationDrawer(
            ownerName = user?.name ?: "",
            drawerState = drawerState,
            logout = { logout() }
        ) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        openUserModal = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    )
                },
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackBarState
                    )
                }
            ) { paddingValues ->
                FeedCity(
                    modifier = Modifier
                        .padding(paddingValues),
                    userOwnerName = user?.name ?: "",
                    userOwnerCity = user?.city ?: "",
                    commentsLength = 1
                )
            }
        }
    }
}
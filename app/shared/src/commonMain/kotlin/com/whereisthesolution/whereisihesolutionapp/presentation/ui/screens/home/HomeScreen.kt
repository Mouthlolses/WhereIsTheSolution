package com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.components.CustomModalNavigationDrawer
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.components.CustomTopAppBar
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.components.FeedCity
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState()
) {

    val user = uiState.user

    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    if (uiState.loading) {
        CircularProgressIndicator()
    } else {
        CustomModalNavigationDrawer(
            drawerState = drawerState
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
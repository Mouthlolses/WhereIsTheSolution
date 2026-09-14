package com.whereisthesolution.whereisihesolutionapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whereisthesolution.whereisihesolutionapp.navigation.AppNavHost

@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavHost()
    }
}
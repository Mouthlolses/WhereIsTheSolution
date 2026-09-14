package com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.components.CustomTopAppBar
import com.whereisthesolution.whereisihesolutionapp.presentation.ui.components.FeedCity

@Preview(showBackground = true)
@Composable
fun HomeScreen(){

    Scaffold(
        topBar = {
            CustomTopAppBar()
        }
    ){ paddingValues ->

        FeedCity(
            modifier = Modifier
                .padding(paddingValues)
        )

    }


}
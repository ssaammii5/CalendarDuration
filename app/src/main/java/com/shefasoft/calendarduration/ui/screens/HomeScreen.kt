package com.shefasoft.calendarduration.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    TopAppBar(title = {
        Text("Calendar Duration")
    })
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
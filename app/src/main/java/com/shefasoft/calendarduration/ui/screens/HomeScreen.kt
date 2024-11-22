package com.shefasoft.calendarduration.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shefasoft.calendarduration.ui.components.DateSelector
import com.shefasoft.calendarduration.ui.components.TimeGridMatrix
import com.shefasoft.calendarduration.ui.components.TimeTrackingCard
import com.shefasoft.calendarduration.ui.components.TopAppBarComponent

@Composable
fun HomeScreen() {

    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TopAppBarComponent()
        DateSelector()
        TimeTrackingCard()
        TimeGridMatrix()
        Spacer(Modifier.padding(vertical = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
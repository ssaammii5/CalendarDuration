package com.shefasoft.calendarduration.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import com.shefasoft.calendarduration.ui.components.DateSelector
import com.shefasoft.calendarduration.ui.components.EventActivityCard
import com.shefasoft.calendarduration.ui.components.TimeTrackingLine
import com.shefasoft.calendarduration.ui.components.TopAppBarComponent

@Composable
fun EventDetailsScreen(){
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBarComponent()
        DateSelector()
        TimeTrackingLine()
        EventActivityCard()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventDetailsScreen(){
    EventDetailsScreen()
}
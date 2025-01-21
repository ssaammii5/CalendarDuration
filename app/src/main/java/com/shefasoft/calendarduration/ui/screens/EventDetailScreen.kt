package com.shefasoft.calendarduration.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shefasoft.calendarduration.ui.components.DateSelector
import com.shefasoft.calendarduration.ui.components.EventActivityCard
import com.shefasoft.calendarduration.ui.components.TimeTrackingLine
import com.shefasoft.calendarduration.viewModel.MainViewModel
import com.shefasoft.calendarduration.viewModel.UiState

@Composable
fun EventDetailsScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val uiState = viewModel.uiState.value
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        DateSelector()
        TimeTrackingLine()
        if (uiState is UiState.ShowEventsList) {
            EventActivityCard(events = uiState.events)
        } else {
            // Handle other states or show a fallback UI
            Text("No events available.")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewEventDetailsScreen(){
//    EventDetailsScreen()
//}
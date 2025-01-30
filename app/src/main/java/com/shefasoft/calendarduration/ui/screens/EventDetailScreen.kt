package com.shefasoft.calendarduration.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.shefasoft.calendarduration.ui.components.DateSelector
import com.shefasoft.calendarduration.ui.components.EventActivityCard
import com.shefasoft.calendarduration.ui.components.TimeTrackingLine
import com.shefasoft.calendarduration.ui.components.localCal.LoadingScreen
import com.shefasoft.calendarduration.viewModel.MainViewModel
import com.shefasoft.calendarduration.viewModel.UiState

@Composable
fun EventDetailsScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val uiState = viewModel.uiState.value
    val selectedDate = viewModel.selectedDate.value

    LaunchedEffect(viewModel.selectedDate.value) {
        viewModel.loadCalendarEvents()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        DateSelector(
            // Pass the latest date directly
            selectedDate = selectedDate,
            onDateSelected = { newDate ->
                viewModel.updateSelectedDate(newDate)
            }
        )
        TimeTrackingLine(viewModel = viewModel)
        when (uiState) {
            is UiState.ShowEventsList -> {
                EventActivityCard(events = uiState.events)
            }
            is UiState.Loading -> {
                LoadingScreen()
            }
            else -> {
                Text("No events available.")
            }
        }
    }
}
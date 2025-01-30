package com.shefasoft.calendarduration.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shefasoft.calendarduration.ui.components.DateSelector
import com.shefasoft.calendarduration.ui.components.EventActivityCard
import com.shefasoft.calendarduration.ui.components.TimeTrackingLine
import com.shefasoft.calendarduration.ui.components.localCal.LoadingScreen
import com.shefasoft.calendarduration.viewModel.MainViewModel
import com.shefasoft.calendarduration.viewModel.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EventDetailsScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val uiState = viewModel.uiState.value
    val selectedDate = viewModel.selectedDate.value
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(selectedDate) {
        viewModel.loadCalendarEvents()
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            scope.launch {
                isRefreshing = true
                viewModel.loadCalendarEvents() // Refresh calendar events
                delay(1500) // Simulate network delay (optional)
                isRefreshing = false
            }
        }
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top
        ) {
            DateSelector(
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
}

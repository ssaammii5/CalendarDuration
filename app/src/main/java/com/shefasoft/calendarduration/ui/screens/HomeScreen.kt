package com.shefasoft.calendarduration.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shefasoft.calendarduration.ui.components.DateSelector
import com.shefasoft.calendarduration.ui.components.TimeGridMatrix
import com.shefasoft.calendarduration.ui.components.TimeTrackingCard
import com.shefasoft.calendarduration.viewModel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
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
            modifier = modifier
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            DateSelector(
                selectedDate = selectedDate,
                onDateSelected = { newDate ->
                    viewModel.updateSelectedDate(newDate)
                }
            )

            TimeTrackingCard(viewModel = viewModel)
            TimeGridMatrix(viewModel = viewModel)
            Spacer(Modifier.padding(vertical = 16.dp))
        }
    }
}

package com.shefasoft.calendarduration.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shefasoft.calendarduration.ui.components.DateSelector
import com.shefasoft.calendarduration.ui.components.TimeGridMatrix
import com.shefasoft.calendarduration.ui.components.TimeTrackingCard
import com.shefasoft.calendarduration.viewModel.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val selectedDate = viewModel.selectedDate.value

    LaunchedEffect(selectedDate) {
        viewModel.loadCalendarEvents()
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DateSelector(
            // Pass the latest date directly
            selectedDate = selectedDate,
            onDateSelected = { newDate ->
                viewModel.updateSelectedDate(newDate)
            }
        )

        TimeTrackingCard(
            viewModel = viewModel
        )
        TimeGridMatrix(
            viewModel = viewModel
        )
        Spacer(Modifier.padding(vertical = 16.dp))
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen()
//}
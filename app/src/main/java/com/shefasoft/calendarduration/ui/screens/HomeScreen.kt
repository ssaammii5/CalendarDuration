package com.shefasoft.calendarduration.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shefasoft.calendarduration.ui.components.DateSelector
import com.shefasoft.calendarduration.ui.components.TimeGridMatrix
import com.shefasoft.calendarduration.ui.components.TimeTrackingCard
import com.shefasoft.calendarduration.viewModel.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val selectedDate = remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DateSelector{ date ->
            selectedDate.value = date
        }
        TimeTrackingCard(
            viewModel = viewModel
        )
        TimeGridMatrix()
        Spacer(Modifier.padding(vertical = 16.dp))
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen()
//}
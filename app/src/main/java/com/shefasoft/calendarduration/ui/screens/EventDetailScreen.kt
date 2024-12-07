package com.shefasoft.calendarduration.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shefasoft.calendarduration.ui.components.DateSelector
import com.shefasoft.calendarduration.ui.components.EventActivityCard
import com.shefasoft.calendarduration.ui.components.TimeTrackingLine

@Composable
fun EventDetailsScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        DateSelector()
        TimeTrackingLine()
        EventActivityCard()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewEventDetailsScreen(){
//    EventDetailsScreen()
//}
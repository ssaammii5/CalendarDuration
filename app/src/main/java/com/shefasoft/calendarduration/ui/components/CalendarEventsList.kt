package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shefasoft.calendarduration.model.CalendarEvent

@Composable
fun CalendarEventsList(events: List<CalendarEvent>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(events.size) { index ->
            val event = events[index]
            Card(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(Color(event.color ?: 0))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Title: ${event.title}")
                    }
                    Text(text = "Calendar: ${event.calendarName}")
                    Text(text = "Start Time: ${event.startTime}")
                    Text(text = "End Time: ${event.endTime}")
                    event.description?.let {
                        Text(text = "Description: $it")
                    }
                    event.location?.let {
                        Text(text = "Location: $it")
                    }
                }
            }
        }
    }
}
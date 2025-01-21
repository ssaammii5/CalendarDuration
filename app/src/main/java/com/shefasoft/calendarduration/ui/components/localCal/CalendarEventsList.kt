package com.shefasoft.calendarduration.ui.components.localCal


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shefasoft.calendarduration.model.CalendarEvent

@Composable
fun CalendarEventsList(events: List<CalendarEvent>, onReselect: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            items(events.size) { index ->
                val event = events[index]
                Card(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                            Text(text = "Title: ${event.title}", style = MaterialTheme.typography.bodyLarge)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Calendar: ${event.calendarName}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Start Time: ${event.startTime}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "End Time: ${event.endTime}", style = MaterialTheme.typography.bodyMedium)
                        event.description?.let {
                            Text(text = "Description: $it", style = MaterialTheme.typography.bodyMedium)
                        }
                        event.location?.let {
                            Text(text = "Location: $it", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
        Button(
            onClick = onReselect,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Reselect Emails and Calendars")
        }
    }
}

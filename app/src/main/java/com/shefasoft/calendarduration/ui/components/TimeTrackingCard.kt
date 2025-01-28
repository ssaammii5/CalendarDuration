package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shefasoft.calendarduration.R
import com.shefasoft.calendarduration.model.CalendarEvent
import com.shefasoft.calendarduration.ui.components.homeScreen.AlertDialogEvents
import com.shefasoft.calendarduration.viewModel.MainViewModel
import com.shefasoft.calendarduration.viewModel.UiState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun TimeTrackingCard(viewModel: MainViewModel) {
    val uiState = viewModel.uiState.value

    val calendarEventGroups = remember(uiState) {
        if (uiState is UiState.ShowEventsList) {
            uiState.events.groupBy { it.calendarName }
        } else {
            emptyMap<String, List<CalendarEvent>>()
        }
    }

    val totalDuration = remember(calendarEventGroups) {
        calendarEventGroups.values.flatten().sumOf { event ->
            try {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val startMillis = format.parse(event.startTime)?.time ?: 0
                val endMillis = format.parse(event.endTime)?.time ?: 0
                endMillis - startMillis // in ms
            } catch (e: Exception) {
                0
            }
        }
    }

    // Convert totalDuration from milliseconds to hours/minutes as a string
    val (hours, minutes) = remember(totalDuration) {
        val totalMinutes = totalDuration / (1000 * 60)
        Pair(totalMinutes / 60, totalMinutes % 60)
    }

    var selectedCalendarEvents by remember { mutableStateOf<List<CalendarEvent>?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val selectedDate = viewModel.selectedDate.value
        val dateText = remember(selectedDate.timeInMillis) {
            getFormattedDateHome(selectedDate)
        }

        // Total Time Display
        Text(
            text = "$hours hr, $minutes min",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SimpleRotatedText(dateText)

            // Bar Chart with Time Labels on the Right
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                BarChart(calendarEventGroups, onBarClick = { calendarName ->
                    selectedCalendarEvents = calendarEventGroups[calendarName]
                })
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        calendarEventGroups.toSortedMap().forEach { (calendarName, events) ->
            // Optionally compute the total time for these events alone
            val calendarDuration = events.sumOf { event ->
                try {
                    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val startMillis = format.parse(event.startTime)?.time ?: 0
                    val endMillis = format.parse(event.endTime)?.time ?: 0
                    endMillis - startMillis
                } catch (e: Exception) {
                    0
                }
            }
            val minutesForCalendar = calendarDuration / (1000 * 60)
            val hoursForCalendar = minutesForCalendar / 60
            val leftoverMinutes = minutesForCalendar % 60

            val colorInt = events.firstOrNull()?.color ?: 0xFF000000.toInt() // fallback if empty
            val color = Color(colorInt)

            // Render a row, similar to your existing ActivityItem pattern
            ActivityItemWithDivider(
                color = color,             // Could assign color by calendar
                name = calendarName,           // The calendar name
                events = events.size,          // Number of events in this calendar
                time = "${hoursForCalendar}h ${leftoverMinutes}m", // Sum of durations
                onClick = {
                    selectedCalendarEvents = events
                }
            )
        }
    }

    selectedCalendarEvents?.let { events ->
        AlertDialog(
            onDismissRequest = { selectedCalendarEvents = null },
            title = {
                Text(text = "Events")
            },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                ) {
                    AlertDialogEvents(events = events)
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedCalendarEvents = null }) {
                    Text(text = "Close")
                }
            }
        )
    }
}

@Composable
fun BarChart(calendarEventGroups: Map<String, List<CalendarEvent>>, onBarClick: (String) -> Unit) {
    val maxDuration = calendarEventGroups.values.flatten().maxOfOrNull { event ->
        try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val startMillis = format.parse(event.startTime)?.time ?: 0
            val endMillis = format.parse(event.endTime)?.time ?: 0
            endMillis - startMillis
        } catch (e: Exception) {
            0
        }
    } ?: 0

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Container for the bar chart and line
        Box(
            modifier = Modifier
                .weight(1f) // Occupies available width except for the labels
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 1.dp), // Space for the line below the bars
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.Bottom
                ) {
                    calendarEventGroups.toSortedMap().forEach { (calendarName, events) ->
                        val calendarDuration = events.sumOf { event ->
                            try {
                                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                val startMillis = format.parse(event.startTime)?.time ?: 0
                                val endMillis = format.parse(event.endTime)?.time ?: 0
                                endMillis - startMillis
                            } catch (e: Exception) {
                                0
                            }
                        }

                        val colorInt = events.firstOrNull()?.color ?: 0xFF000000.toInt()
                        val color = Color(colorInt)

                        Box(
                            modifier = Modifier
                                .width(32.dp)
                                .fillMaxHeight((calendarDuration.toFloat() / maxDuration.toFloat()).coerceAtMost(1f))
                                .background(color = color, shape = MaterialTheme.shapes.small)
                                .clickable { onBarClick(calendarName) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            // Line below the bar chart only, occupying full width of the bars
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
                    .align(Alignment.BottomCenter)
            )
        }

        // Time labels on the right side
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.height(100.dp)
        ) {
            Text(text = "${(maxDuration / (1000 * 60 * 60))}h", fontSize = 12.sp, color = Color.Gray)
            Text(text = "${((maxDuration * 0.75) / (1000 * 60 * 60)).toInt()}h", fontSize = 12.sp, color = Color.Gray)
            Text(text = "${((maxDuration * 0.5) / (1000 * 60 * 60)).toInt()}h", fontSize = 12.sp, color = Color.Gray)
            Text(text = "${((maxDuration * 0.25) / (1000 * 60 * 60)).toInt()}h", fontSize = 12.sp, color = Color.Gray)
            Text(text = "0h", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ActivityItemWithDivider(color: Color, name: String, events: Int, time: String, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable { onClick() }) {
        ActivityItem(color = color, name = name, events = events, time = time)
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 4.dp)
        )
    }
}

@Composable
fun ActivityItem(color: Color, name: String, events: Int, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontWeight = FontWeight.Bold)
            Text(text = "Event: $events", fontSize = 12.sp, color = Color.Gray)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_clock_duration),
                contentDescription = "Time Icon",
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))
            Text(text = time, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SimpleRotatedText(text: String) {
    Canvas(
        modifier = Modifier
            .height(100.dp)
            .width(30.dp)
    ) {
        val paint = Paint().asFrameworkPaint().apply {
            color = android.graphics.Color.BLACK
            textSize = 18.sp.toPx()
            isAntiAlias = true
            textAlign = android.graphics.Paint.Align.CENTER
        }

        drawContext.canvas.nativeCanvas.apply {
            save()
            rotate(-90f, size.width / 2, size.height / 2)
            drawText(text, size.width / 2, size.height / 2, paint)
            restore()
        }
    }
}

private fun getFormattedDateHome(calendar: Calendar): String {
    val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

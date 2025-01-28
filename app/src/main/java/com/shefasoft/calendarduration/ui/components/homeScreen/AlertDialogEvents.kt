package com.shefasoft.calendarduration.ui.components.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shefasoft.calendarduration.R
import com.shefasoft.calendarduration.model.CalendarEvent
import com.shefasoft.calendarduration.ui.components.EventActivityCard
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AlertDialogEvents(events: List<CalendarEvent>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
            //.padding(16.dp)
            //.background(Color.White, shape = MaterialTheme.shapes.medium)
            //.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(events.size) { index ->
            val event = events[index]
            val duration = calculateDuration(event.startTime, event.endTime)
            EAItemWithDivider(
                color = Color(event.color ?: 0),
                name = event.title,
                calendarName = event.calendarName,
                time = duration,
                timeframe = "${formatTime(event.startTime)} - ${formatTime(event.endTime)}"
            )
        }
    }
}


@Composable
fun EAItemWithDivider(
    color: Color, name: String, calendarName: String, time: String, timeframe:
    String
) {
    Column {
        EventActivityItem(
            color = color,
            name = name,
            calendar_name = calendarName,
            time = time,
            timeframe = timeframe
        )
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 4.dp)
        )
    }
}

@Composable
fun EventActivityItem(
    color: Color, name: String, calendar_name: String, time: String, timeframe:
    String
) {
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
            Text(text = name, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Text(text = calendar_name, fontSize = 14.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clock_duration),
                    contentDescription = "Time Icon",
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))
                Text(text = time, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Text(
                text = timeframe,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun EventActivityCardPreview() {
    EventActivityCard(
        events = listOf(
            CalendarEvent(
                color = 0xFF388E3C.toInt(),
                title = "Long Event Name That Might Cause Problems",
                calendarName = "A Very Long Calendar Name That Should Be Truncated",
                startTime = "10:00 AM",
                endTime = "12:00 PM",
                description = "This is a sample description for the event.",
                location = "Sample Location"
            )
        )
    )
}

fun formatTime(dateTime: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // Input format
    val outputFormatter = DateTimeFormatter.ofPattern("hh:mm a") // Desired output format
    val parsedDateTime = LocalDateTime.parse(dateTime, inputFormatter)
    return parsedDateTime.format(outputFormatter)
}

fun calculateDuration(startTime: String, endTime: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val start = LocalDateTime.parse(startTime, inputFormatter)
    val end = LocalDateTime.parse(endTime, inputFormatter)
    val duration = Duration.between(start, end)

    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    return "${hours}h ${minutes}m"
}
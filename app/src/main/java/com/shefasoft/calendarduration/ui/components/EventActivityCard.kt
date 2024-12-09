package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shefasoft.calendarduration.R

@Composable
fun EventActivityCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EAItemWithDivider(color = Color.Blue, name = "Lunch", calendar_name = "Activity", time = "9h 45m",  timeframe = "10:30 AM - 12:00 PM")
        EAItemWithDivider(color = Color(0xFF388E3C), name = "Mathematics", calendar_name = "Study", time = "7h 30m", timeframe = "09:15 AM - 12:45 PM")
        EAItemWithDivider(color = Color.Gray, name = "Facebook", calendar_name = "Procrastination", time = "5h 15m", timeframe = "08:00 AM - 09:30 AM")
        EAItemWithDivider(color = Color.Red, name = "Programming", calendar_name = "CP", time = "4h 30m", timeframe = "07:30 AM - 08:00 PM")
        EAItemWithDivider(color = Color(0xFF388E3C), name = "Biology", calendar_name = "Study", time = "7h 30m", timeframe = "09:15 AM - 12:45 PM")
        EAItemWithDivider(color = Color.Blue, name = "Dinner", calendar_name = "Activity", time = "9h 45m", timeframe = "10:30 AM - 12:00 PM")
    }
}


@Composable
fun EAItemWithDivider(color: Color, name: String, calendar_name: String, time: String, timeframe:
String) {
    Column {
        EventActivityItem(
            color = color,
            name = name,
            calendar_name = calendar_name,
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
fun EventActivityItem(color: Color, name: String, calendar_name: String, time: String, timeframe:
String) {
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
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Text(text = calendar_name, fontSize = 14.sp, color = Color.Gray)
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
            Text(text = timeframe,fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Gray)
        }
    }
}


@Composable
@Preview
fun EventActivityCardPreview() {
    EventActivityCard()
}

package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.Image
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

        Spacer(modifier = Modifier.height(20.dp))

        // Activity List with Dividers
        EAItemWithDivider(color = Color.Blue, name = "Activity", events = 5, time = "9h 45m")
        EAItemWithDivider(color = Color(0xFF388E3C), name = "Study", events = 2, time = "7h 30m")
        EAItemWithDivider(color = Color.Gray, name = "Procrastination", events = 1, time = "5h 15m")
        EAItemWithDivider(color = Color.Red, name = "Programming", events = 2, time = "4h 30m")
    }
}




@Composable
fun EAItemWithDivider(color: Color, name: String, events: Int, time: String) {
    Column {
        EventActivityItem(color = color, name = name, events = events, time = time)
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 4.dp)
        )
    }
}

@Composable
fun EventActivityItem(color: Color, name: String, events: Int, time: String) {
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
            Text(text = "Event: $events", fontSize = 12.sp, color = Color.Gray)
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_clock_duration), // Replace with your vector drawable
                    contentDescription = "Time Icon",
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))
                Text(text = time, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Text(text = "10:30 AM - 12:00 PM", fontWeight = FontWeight.Normal, color = Color.Gray)
        }
    }
}



@Composable
@Preview
fun EventActivityCardPreview() {
    EventActivityCard()
}

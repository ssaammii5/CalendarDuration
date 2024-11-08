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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalendarDetails() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        CalendarItems(
            title = "Fresh, Bath",
            subtitle = "Activity",
            time = "10:30 AM - 12:00 PM",
            duration = "1h 30m",
            color = Color.Blue
        )
        CalendarItems(
            title = "Study",
            subtitle = "Study",
            time = "10:30 AM - 12:00 PM",
            duration = "1h 30m",
            color = Color.Green
        )
        CalendarItems(
            title = "Facebook, Youtube",
            subtitle = "Procrastination",
            time = "10:30 AM - 12:00 PM",
            duration = "1h 30m",
            color = Color.Gray
        )
    }
}

@Composable
fun CalendarItems(
    title: String,
    subtitle: String,
    time: String,
    duration: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = com.shefasoft.calendarduration.R.drawable.ic_clock_duration),
                    contentDescription = "Duration Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = duration,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.End
                )
            }
            Text(
                text = time,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun CalendarDetailsPreview() {
    CalendarDetails()
}
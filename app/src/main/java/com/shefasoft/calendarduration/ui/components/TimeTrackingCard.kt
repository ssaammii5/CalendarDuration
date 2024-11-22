package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shefasoft.calendarduration.R

@Composable
fun TimeTrackingCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Total Time Display
        Text(
            text = "5 hr, 30 min",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Rotated Date on the Left Side
//            Text(
//                text = "Nov 05, 2024",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black,
//                modifier = Modifier
//                    .rotate(-90f)
//                    //.padding(end = 16.dp)
//            )
            SimpleRotatedText("Nov 05, 2024")

            // Bar Chart with Time Labels on the Right
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                BarChart()
            }

            // Time Labels on the Right
//            Column(
//                verticalArrangement = Arrangement.SpaceBetween,
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.height(100.dp)
//            ) {
//                Text(text = "15h", fontSize = 12.sp, color = Color.Gray)
//                Text(text = "12h", fontSize = 12.sp, color = Color.Gray)
//                Text(text = "6h", fontSize = 12.sp, color = Color.Gray)
//                Text(text = "0h", fontSize = 12.sp, color = Color.Gray)
//            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Activity List with Dividers
        ActivityItemWithDivider(color = Color.Blue, name = "Activity", events = 5, time = "9h 45m")
        ActivityItemWithDivider(color = Color(0xFF388E3C), name = "Study", events = 2, time = "7h 30m")
        ActivityItemWithDivider(color = Color.Gray, name = "Procrastination", events = 1, time = "5h 15m")
        ActivityItemWithDivider(color = Color.Red, name = "Programming", events = 2, time = "4h 30m")
    }
}

@Composable
fun xxBarChart() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.Bottom
    ) {
        Bar(color = Color.Blue, heightFraction = 0.7f)
        Spacer(modifier = Modifier.width(8.dp))
        Bar(color = Color(0xFF388E3C), heightFraction = 0.5f)
        Spacer(modifier = Modifier.width(8.dp))
        Bar(color = Color.Gray, heightFraction = 0.4f)
        Spacer(modifier = Modifier.width(8.dp))
        Bar(color = Color.Red, heightFraction = 0.3f)
        Spacer(modifier = Modifier.width(8.dp))

    }
}

@Composable
fun BarChart() {
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
                    Bar(color = Color.Blue, heightFraction = 0.7f)
                    Spacer(modifier = Modifier.width(8.dp))
                    Bar(color = Color(0xFF388E3C), heightFraction = 0.5f)
                    Spacer(modifier = Modifier.width(8.dp))
                    Bar(color = Color.Gray, heightFraction = 0.4f)
                    Spacer(modifier = Modifier.width(8.dp))
                    Bar(color = Color.Red, heightFraction = 0.3f)
                    Spacer(modifier = Modifier.width(8.dp))
                    Bar(color = Color.Red, heightFraction = 0.3f)
                    Spacer(modifier = Modifier.width(8.dp))
                    Bar(color = Color.Red, heightFraction = 0.3f)
                    Spacer(modifier = Modifier.width(8.dp))
                    Bar(color = Color.Red, heightFraction = 0.3f)
                    Spacer(modifier = Modifier.width(8.dp))
                    Bar(color = Color.Red, heightFraction = 0.3f)
                    Spacer(modifier = Modifier.width(8.dp))
                    Bar(color = Color.Red, heightFraction = 0.3f)
                    Spacer(modifier = Modifier.width(8.dp))

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
            Text(text = "15h", fontSize = 12.sp, color = Color.Gray)
            Text(text = "12h", fontSize = 12.sp, color = Color.Gray)
            Text(text = "6h", fontSize = 12.sp, color = Color.Gray)
            Text(text = "0h", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun Bar(color: Color, heightFraction: Float) {
    Box(
        modifier = Modifier
            .width(32.dp)
            .fillMaxHeight(heightFraction)
            .background(color = color, shape = MaterialTheme.shapes.small)
    )
}

@Composable
fun ActivityItemWithDivider(color: Color, name: String, events: Int, time: String) {
    Column {
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
                painter = painterResource(id = R.drawable.ic_clock_duration), // Replace with your vector drawable
                contentDescription = "Time Icon",
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))
            Text(text = time, fontWeight = FontWeight.Bold)
        }
        //Text(text = time, fontWeight = FontWeight.Bold)
    }
}



@Composable
fun SimpleRotatedText(text: String) {
    Canvas(
        modifier = Modifier
            .height(100.dp) // Adjust the height to fit the text
            .width(30.dp)   // Adjust the width for alignment control if necessary
    ) {
        val paint = Paint().asFrameworkPaint().apply {
            color = android.graphics.Color.BLACK
            textSize = 18.sp.toPx() // Convert sp to px for text size
            isAntiAlias = true
            textAlign = android.graphics.Paint.Align.CENTER
        }

        // Rotate the canvas before drawing the text
        drawContext.canvas.nativeCanvas.apply {
            save() // Save the current canvas state
            rotate(-90f, size.width / 2, size.height / 2) // Rotate around the center
            drawText(text, size.width / 2, size.height / 2, paint)
            restore() // Restore the canvas to its original state
        }
    }
}

@Composable
@Preview
fun TimeTrackingCardPreview() {
    TimeTrackingCard()
}

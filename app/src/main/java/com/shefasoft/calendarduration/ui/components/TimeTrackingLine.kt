package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TimeTrackingLine(){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Tue, Nov 5, 2024")

            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "4h 30m", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Arrow",
                    tint = Color.Black,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        TaskDurationBarChartExample()

    }
}


@Composable
fun TaskDurationBarChart(
    tasks: List<Pair<String, Float>>, // List of tasks with their duration in hours
    colors: List<Color> // List of colors for each task segment
) {
    // Calculate total duration manually
    val totalHours = tasks.fold(0f) { sum, task -> sum + task.second }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp) // Set the height of the entire bar
    ) {
        tasks.forEachIndexed { index, task ->
            val taskDuration = task.second
            val taskProportion = taskDuration / totalHours // Calculate the width proportionally

            // Each task segment in the bar with rounded corners
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(taskProportion) // Width based on duration proportion
                    .clip(RoundedCornerShape(5.dp)) // Rounded corners
                    .background(colors[index % colors.size]) // Cycle through colors if needed
            )

            // Add a 2dp space between segments, except after the last segment
            if (index < tasks.size - 1) {
                Spacer(modifier = Modifier.width(2.5.dp))
            }
        }
    }
}

@Composable
fun TaskDurationBarChartExample() {
    val tasks = listOf(
        "Task 1" to 9f,  // Task that took 9 hours
        "Task 2" to 7f,  // Task that took 7 hours
        "Task 3" to 4f,  // Task that took 4 hours
        "Task 4" to 3f,  // Task that took 3 hours
        "Task 5" to 1f,   // Task that took 1 hour
        "Task 6" to 1f   // Task that took 1 hour
    )

    val colors = listOf(
        Color.Blue, Color.Green, Color.Red, Color.Yellow, Color.Magenta, Color.Cyan
    )

    TaskDurationBarChart(tasks = tasks, colors = colors)
}

@Preview(showBackground = true)
@Composable
fun TimeTrackingLinePreview() {
    TimeTrackingLine()
}
package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shefasoft.calendarduration.viewModel.MainViewModel
import com.shefasoft.calendarduration.viewModel.UiState
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TimeTrackingLine(viewModel: MainViewModel) {
    val selectedDate = viewModel.selectedDate.value
    val dateText = remember(selectedDate.timeInMillis) {
        getFormattedDate(selectedDate)
    }

    // Get the total duration from events
    val totalDurationMinutes = remember(viewModel.uiState.value) {
        calculateTotalDuration(viewModel)
    }

    // Format duration into "Xh Ym"
    val formattedDuration = formatDuration(totalDurationMinutes)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = dateText)

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = formattedDuration, fontSize = 14.sp) // Dynamic Duration
                Spacer(modifier = Modifier.width(2.dp))
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
//                    contentDescription = "Arrow",
//                    modifier = Modifier.size(14.dp)
//                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        TaskDurationBarChartExample(viewModel = viewModel)
    }
}

// Function to calculate total duration in minutes
fun calculateTotalDuration(viewModel: MainViewModel): Int {
    val uiState = viewModel.uiState.value
    return if (uiState is UiState.ShowEventsList) {
        uiState.events.sumOf { event ->
            try {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val startMillis = format.parse(event.startTime)?.time ?: return@sumOf 0
                val endMillis = format.parse(event.endTime)?.time ?: return@sumOf 0
                ((endMillis - startMillis) / (1000 * 60)).toInt() // Convert milliseconds to minutes
            } catch (e: Exception) {
                0
            }
        }
    } else {
        0
    }
}

// Function to format duration into "Xh Ym"
fun formatDuration(totalMinutes: Int): String {
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return when {
        hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
        hours > 0 -> "${hours}h"
        else -> "${minutes}m"
    }
}

@Composable
fun TaskDurationBarChart(
    tasks: List<Pair<String, Float>>, // Task data
    colors: List<Color> // Associated colors
) {
    val totalHours = tasks.sumOf { it.second.toDouble() }.toFloat() // Explicit type conversion
    if (totalHours == 0f) return // Prevent division by zero

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
    ) {
        tasks.forEachIndexed { index, task ->
            val taskProportion = task.second / totalHours
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(taskProportion)
                    .clip(RoundedCornerShape(5.dp))
                    .background(colors[index % colors.size])
            )
            if (index < tasks.size - 1) {
                Spacer(modifier = Modifier.width(2.5.dp))
            }
        }
    }
}

@Composable
fun TaskDurationBarChartExample(viewModel: MainViewModel) {
    val uiState = viewModel.uiState.value
    val allTasks = remember(uiState) {
        if (uiState is UiState.ShowEventsList) {
            uiState.events.flatMap { event ->
                try {
                    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val startMillis = format.parse(event.startTime)?.time ?: return@flatMap emptyList<Pair<String, Float>>()
                    val endMillis = format.parse(event.endTime)?.time ?: return@flatMap emptyList<Pair<String, Float>>()
                    listOf(event.title to (endMillis - startMillis) / (1000 * 60 * 60).toFloat())
                } catch (e: Exception) {
                    emptyList()
                }
            }
        } else {
            emptyList()
        }
    }

    val colors = remember(uiState) {
        if (uiState is UiState.ShowEventsList) {
            uiState.events.map {
                val colorInt = it.color ?: 0xFF000000.toInt() // Default to black if color is null or invalid
                Color(colorInt)
            }
        } else {
            emptyList<Color>()
        }
    }

    if (allTasks.isNotEmpty()) {
        TaskDurationBarChart(
            tasks = allTasks,
            colors = colors
        )
    }
}

fun getFormattedDate(selectedDate: java.util.Date): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(selectedDate)
}

package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeGridMatrix(viewModel: MainViewModel) {
    val uiState = viewModel.uiState.value

    // Get events from the UI state
    val events = remember(uiState) {
        if (uiState is UiState.ShowEventsList) {
            uiState.events
        } else {
            emptyList()
        }
    }

    // Helper function to convert a time in "yyyy-MM-dd HH:mm:ss" format to a grid index
    fun getGridRowColForTime(time: String): Pair<Int, Int> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(time, formatter)

        val hours = dateTime.hour  // Extract hour (0-23)
        val minutes = dateTime.minute  // Extract minutes (0-59)

        val row = minutes / 5  // Convert minutes into 5-minute intervals (0-11)
        val col = hours  // The hour directly corresponds to the column (0-23)

        return Pair(row, col)  // Return (row, col) directly
    }

    // Use MaterialTheme colors for Dark Mode compatibility
    val backgroundColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onSurface
    val gridDefaultColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)) // Adds rounded corners
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp))
            .background(backgroundColor) // Background color to make the rounded corners visible
            .padding(16.dp) // Padding inside the rounded border
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Title and subtitle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Time Grids", fontSize = 20.sp, color = textColor)
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = "Displaying cell based along with time",
                fontSize = 14.sp,
                color = textColor.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )

            // Time labels row
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("12:00 AM", "06:00 AM", "12:00 PM", "06:00 PM", "11:00 PM").forEach { time ->
                    Text(text = time, fontSize = 12.sp.nonScaledSp, color = textColor.copy(alpha = 0.6f))
                }
            }

            // Grid of time slots with constrained height
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp) // Constrain the height of the grid
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(24), // Define the number of columns (5-minute intervals)
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Create 288 items (24 columns x 12 rows)
                    items(288) { index ->
                        val row = index / 24  // 5-minute slot row (0-11)
                        val col = index % 24  // Hour column (0-23)

                        val colorForCell = remember(events) {
                            events.firstOrNull { event ->
                                val (eventStartRow, eventStartCol) = getGridRowColForTime(event.startTime)
                                val (eventEndRow, eventEndCol) = getGridRowColForTime(event.endTime)

                                // Fix: Adjust range check to prevent an extra cell being taken
                                val isInTimeRange = when {
                                    eventStartCol == eventEndCol -> {
                                        // Event is within the same hour
                                        col == eventStartCol && row in eventStartRow until eventEndRow // Changed ".." to "until"
                                    }
                                    eventStartCol < eventEndCol -> {
                                        // Event spans multiple hours within the same day
                                        (col == eventStartCol && row >= eventStartRow) ||
                                                (col == eventEndCol && row < eventEndRow) ||  // Changed "<=" to "<"
                                                (col in (eventStartCol + 1)..(eventEndCol - 1))
                                    }
                                    else -> {
                                        // Event spans across midnight (next day)
                                        col >= eventStartCol || col <= eventEndCol
                                    }
                                }

                                // If within range, return the event's color, otherwise default to gray
                                isInTimeRange
                            }?.let { event ->
                                Color(event.color ?: 0xFF000000.toInt())
                            } ?: gridDefaultColor // Default gray color
                        }

                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(1.dp)
                                .background(colorForCell)
                        )
                    }
                }
            }

            // Legend
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "One Grid is 5m", fontSize = 12.sp, color = textColor.copy(alpha = 0.7f))
            }
        }
    }
}

package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun TimeGridMatrix() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)) // Adds rounded corners
            .background(Color.White) // Background color to make the rounded corners visible
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
                Text(text = "Time Grids", fontSize = 20.sp, color = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = "Displaying cell based along with time",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )

            // Time labels row
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("12:00 AM", "06:00 AM", "12:00 PM", "06:00 PM", "11:00 PM").forEach { time ->
                    Text(text = time, fontSize = 12.sp, color = Color.Gray)
                }
            }

            // Grid of time slots with constrained height
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp) // Constrain the height of the grid
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(24), // Define the number of columns
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Create 288 items (24 columns x 12 rows)
                    items(288) { index ->
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f) // Ensures the grid cells are square
                                .padding(1.dp)
                                .background(
                                    color = if (index in 0..285) Color(0xFF4CAF50) else Color(0xFFE0E0E0)
                                    // Highlight some cells
                                )
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
                        .background(Color(0xFF4CAF50))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "One Grid is 5m", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF4CAF50)
fun TimeGridMatrixPreview() {
    TimeGridMatrix()
}

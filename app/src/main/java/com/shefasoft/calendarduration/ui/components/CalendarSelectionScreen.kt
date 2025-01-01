package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shefasoft.calendarduration.model.CalendarInfo

@Composable
fun CalendarSelectionScreen(
    calendars: List<CalendarInfo>,
    onSelectionChange: (Set<Long>) -> Unit,
    onImport: () -> Unit
) {
    val selectedIds = remember { mutableStateOf(calendars.map { it.id }.toSet()) }
    val allSelected = selectedIds.value.size == calendars.size

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = onImport) {
                Text("IMPORT")
            }
        },
        dismissButton = {
            TextButton(onClick = {}) {
                Text("CANCEL")
            }
        },
        title = {
            Text("Select Calendars to import", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Column(
                modifier = Modifier.height(500.dp) // Set fixed height
            ) {
                // "Select All" Option
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            if (allSelected) {
                                selectedIds.value = emptySet()
                            } else {
                                selectedIds.value = calendars.map { it.id }.toSet()
                            }
                            onSelectionChange(selectedIds.value)
                        }
                ) {
                    Text(
                        "Select all",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f), // Use weight to prevent pushing
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Checkbox(
                        checked = allSelected,
                        onCheckedChange = {
                            if (it) {
                                selectedIds.value = calendars.map { it.id }.toSet()
                            } else {
                                selectedIds.value = emptySet()
                            }
                            onSelectionChange(selectedIds.value)
                        }
                    )
                }
                HorizontalDivider()
                LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                    items(calendars) { calendar ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    val updatedSelection = selectedIds.value.toMutableSet()
                                    if (updatedSelection.contains(calendar.id)) {
                                        updatedSelection.remove(calendar.id)
                                    } else {
                                        updatedSelection.add(calendar.id)
                                    }
                                    selectedIds.value = updatedSelection
                                    onSelectionChange(updatedSelection)
                                }
                        ) {
                            // Circular Color Indicator
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape) // Make it circular
                                    .background(Color(calendar.color))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = calendar.name,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f), // Use weight to constrain text
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Checkbox(
                                checked = selectedIds.value.contains(calendar.id),
                                onCheckedChange = { checked ->
                                    val updatedSelection = selectedIds.value.toMutableSet()
                                    if (checked) {
                                        updatedSelection.add(calendar.id)
                                    } else {
                                        updatedSelection.remove(calendar.id)
                                    }
                                    selectedIds.value = updatedSelection
                                    onSelectionChange(updatedSelection)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

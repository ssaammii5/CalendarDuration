package com.shefasoft.calendarduration.ui.components.localCal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shefasoft.calendarduration.model.CalendarInfo


@Composable
fun CalendarSelectionScreen(
    calendars: List<CalendarInfo>,
    onSelectionChange: (Set<Long>) -> Unit,
    onImport: (Set<Long>) -> Unit
) {
    var selectedIds by remember { mutableStateOf(calendars.map { it.id }.toSet()) }
    val allSelected = selectedIds.size == calendars.size

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = { onImport(selectedIds) }) {
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
                            selectedIds = if (allSelected) emptySet() else calendars.map { it.id }.toSet()
                            onSelectionChange(selectedIds)
                        }
                ) {
                    Text(
                        "Select all",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Checkbox(
                        checked = allSelected,
                        onCheckedChange = {
                            selectedIds = if (it) calendars.map { it.id }.toSet() else emptySet()
                            onSelectionChange(selectedIds)
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
                                    selectedIds = if (selectedIds.contains(calendar.id)) {
                                        selectedIds - calendar.id
                                    } else {
                                        selectedIds + calendar.id
                                    }
                                    onSelectionChange(selectedIds)
                                }
                        ) {
                            // Circular Color Indicator
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(Color(calendar.color), shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = calendar.name,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Checkbox(
                                checked = selectedIds.contains(calendar.id),
                                onCheckedChange = { checked ->
                                    selectedIds = if (checked) {
                                        selectedIds + calendar.id
                                    } else {
                                        selectedIds - calendar.id
                                    }
                                    onSelectionChange(selectedIds)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

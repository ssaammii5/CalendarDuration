package com.shefasoft.calendarduration.ui.components.localCal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmailSelectionScreen(
    emails: List<String>,
    onEmailSelected: (String) -> Unit
) {
    var selectedEmail by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = {
                    selectedEmail?.let { onEmailSelected(it) }
                },
                enabled = selectedEmail != null
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = {}) {
                Text("CANCEL")
            }
        },
        title = {
            Text("Select Email")
        },
        text = {
            Column {
                Text("Choose one of your emails to import calendars.")
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp) // Adjust height as needed
                ) {
                    LazyColumn {
                        items(emails) { email ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { selectedEmail = email }
                            ) {
                                RadioButton(
                                    selected = selectedEmail == email,
                                    onClick = { selectedEmail = email }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = email,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

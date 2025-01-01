package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun EmailSelectionScreen(
    emails: List<String>,
    onEmailSelected: (String) -> Unit
) {
    val selectedEmail = remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = {
                    selectedEmail.value?.let { onEmailSelected(it) }
                },
                enabled = selectedEmail.value != null
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
                        .height(400.dp) // Adjust height to control how much content is visible at once
                ) {
                    LazyColumn {
                        items(emails) { email ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { selectedEmail.value = email }
                            ) {
                                RadioButton(
                                    selected = selectedEmail.value == email,
                                    onClick = { selectedEmail.value = email }
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
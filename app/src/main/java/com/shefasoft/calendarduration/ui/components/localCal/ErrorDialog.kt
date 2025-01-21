package com.shefasoft.calendarduration.ui.components.localCal

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
fun ErrorDialog(title: String, message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text("OK")
            }
        },
        title = {
            Text(title)
        },
        text = {
            Text(message)
        },
        properties = DialogProperties(dismissOnClickOutside = false)
    )
}

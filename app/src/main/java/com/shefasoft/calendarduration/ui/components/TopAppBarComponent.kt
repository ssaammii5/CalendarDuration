package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shefasoft.calendarduration.R

@Composable
fun TopAppBarComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Calendar Duration",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButtonWithHighlight(
                icon = R.drawable.ic_calendars,
                isSelected = true // First button is selected
            )
            IconButtonWithHighlight(
                icon = R.drawable.ic_clock_duration,
                isSelected = false
            )
            IconButtonWithHighlight(
                icon = R.drawable.ic_settings,
                isSelected = false
            )
        }
    }
}

@Composable
fun IconButtonWithHighlight(icon: Int, isSelected: Boolean) {
    IconButton(
        onClick = { /* Handle click */ },
        modifier = Modifier
            .background(
            color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color
                .Transparent,
            shape = RoundedCornerShape(40)
        )
    ) {
        Icon(
            painter= painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme
                .onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopAppBarComponent() {
    TopAppBarComponent()
}

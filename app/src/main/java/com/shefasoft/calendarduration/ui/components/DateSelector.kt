package com.shefasoft.calendarduration.ui.components

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shefasoft.calendarduration.ui.theme.greenTextSelected
import com.shefasoft.calendarduration.ui.theme.lightGreen
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DateSelector() {
    val calendar = remember { Calendar.getInstance() }
    val dateState = remember { mutableStateOf(getFormattedDate(calendar)) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Transparent)
    ) {
        // Tab Row for Day, Week, etc.
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .border(1.dp, Color.Black, shape = RoundedCornerShape(50))
//                .padding(horizontal = 0.dp, vertical = 0.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            TabItem("Day", selected = true)
//            TabItem("Week", selected = false)
//            TabItem("Month", selected = false)
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))

        // Date Selector Row with arrows and date
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                // Handle Previous Date
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                dateState.value = getFormattedDate(calendar)
            }) {
                Icon(Icons.AutoMirrored.Default.KeyboardArrowLeft, contentDescription = "Previous")
            }

            Text(
                text = dateState.value,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(50))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable {
                        showDatePicker(context, calendar) {
                            dateState.value = getFormattedDate(calendar)
                        }
                    }
            )

            IconButton(onClick = {
                // Handle Next Date
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                dateState.value = getFormattedDate(calendar)
            }) {
                Icon(Icons.AutoMirrored.Default.KeyboardArrowRight, contentDescription = "Next")
            }
        }
    }
}

@Composable
fun TabItem(text: String, selected: Boolean) {
    Box(
        modifier = Modifier
            .background(
                if (selected) lightGreen else Color.Transparent,
                shape = RoundedCornerShape(50)
            )
            .border(1.dp, if (selected) Color.Black else Color.Transparent, shape = RoundedCornerShape(50))
            .padding(horizontal = 30.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (selected) greenTextSelected else Color.Black,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

fun getFormattedDate(calendar: Calendar): String {
    val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

fun showDatePicker(
    context: android.content.Context,
    calendar: Calendar,
    onDateSelected: () -> Unit
) {
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

@Preview(showBackground = true)
@Composable
fun PreviewDateSelector() {
    DateSelector()
}

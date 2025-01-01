package com.shefasoft.calendarduration.model

data class CalendarEvent(
    val title: String,
    val calendarName: String,
    val startTime: String,
    val endTime: String,
    val description: String?,
    val location: String?,
    val color: Int?
)
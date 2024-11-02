package com.shefasoft.calendarduration.model

data class CalendarData(
    val id: String,
    val name: String,
    val events: List<CalendarEvent>
)
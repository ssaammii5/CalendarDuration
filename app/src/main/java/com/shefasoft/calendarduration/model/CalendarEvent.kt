package com.shefasoft.calendarduration.model

import java.time.Duration
import java.time.LocalDateTime

data class CalendarEvent(
    val id: String,
    val title: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val duration: Duration
)
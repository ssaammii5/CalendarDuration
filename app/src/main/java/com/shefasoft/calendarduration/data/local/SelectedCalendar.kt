package com.shefasoft.calendarduration.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_calendars")
data class SelectedCalendar(
    @PrimaryKey val calendarId: Long
)

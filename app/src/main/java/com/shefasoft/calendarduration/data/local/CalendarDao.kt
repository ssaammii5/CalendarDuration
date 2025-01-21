package com.shefasoft.calendarduration.data.local

import androidx.room.*

@Dao
interface CalendarDao {
    @Query("SELECT email FROM selected_email LIMIT 1")
    suspend fun getSelectedEmail(): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedEmail(selectedEmail: SelectedEmail)

    @Query("DELETE FROM selected_email")
    suspend fun clearSelectedEmail()

    @Query("SELECT calendarId FROM selected_calendars")
    suspend fun getSelectedCalendars(): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedCalendars(calendars: List<SelectedCalendar>)

    @Query("DELETE FROM selected_calendars")
    suspend fun clearSelectedCalendars()
}

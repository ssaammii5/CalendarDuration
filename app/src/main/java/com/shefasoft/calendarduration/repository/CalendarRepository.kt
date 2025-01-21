package com.shefasoft.calendarduration.repository

import android.content.ContentResolver
import com.shefasoft.calendarduration.data.local.CalendarDao
import com.shefasoft.calendarduration.data.local.SelectedCalendar
import com.shefasoft.calendarduration.data.local.SelectedEmail
import com.shefasoft.calendarduration.model.CalendarEvent
import com.shefasoft.calendarduration.model.CalendarInfo
import com.shefasoft.calendarduration.utils.CalendarUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalendarRepository(private val calendarDao: CalendarDao, private val contentResolver: ContentResolver) {

    suspend fun getSelectedEmail(): String? = withContext(Dispatchers.IO) {
        calendarDao.getSelectedEmail()
    }

    suspend fun insertSelectedEmail(email: String) = withContext(Dispatchers.IO) {
        calendarDao.insertSelectedEmail(SelectedEmail(email))
    }

    suspend fun clearSelectedEmail() = withContext(Dispatchers.IO) {
        calendarDao.clearSelectedEmail()
    }

    suspend fun getSelectedCalendars(): List<Long> = withContext(Dispatchers.IO) {
        calendarDao.getSelectedCalendars()
    }

    suspend fun insertSelectedCalendars(calendarIds: List<Long>) = withContext(Dispatchers.IO) {
        val calendars = calendarIds.map { SelectedCalendar(it) }
        calendarDao.insertSelectedCalendars(calendars)
    }

    suspend fun clearSelectedCalendars() = withContext(Dispatchers.IO) {
        calendarDao.clearSelectedCalendars()
    }

    suspend fun getAvailableEmails(): List<String> = withContext(Dispatchers.IO) {
        CalendarUtils.getAvailableEmails(contentResolver)
    }

    suspend fun getCalendarsForEmail(email: String): List<CalendarInfo> = withContext(Dispatchers.IO) {
        CalendarUtils.getCalendarsForEmail(contentResolver, email)
    }

    suspend fun getCalendarEvents(selectedCalendars: Set<Long>): List<CalendarEvent> = withContext(Dispatchers.IO) {
        CalendarUtils.getCalendarEvents(contentResolver, selectedCalendars)
    }
}
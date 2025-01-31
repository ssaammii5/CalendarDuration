package com.shefasoft.calendarduration.utils

import android.content.ContentResolver
import android.provider.CalendarContract
import android.util.Log
import com.shefasoft.calendarduration.model.CalendarEvent
import com.shefasoft.calendarduration.model.CalendarInfo
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtils {

    private const val TAG = "CalendarUtils"

    fun getAvailableEmails(contentResolver: ContentResolver): List<String> {
        val emailList = mutableSetOf<String>()
        return try {
            val uri = CalendarContract.Calendars.CONTENT_URI
            val projection = arrayOf(CalendarContract.Calendars.ACCOUNT_NAME)

            contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val emailIndex = cursor.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME)
                    do {
                        emailList.add(cursor.getString(emailIndex))
                    } while (cursor.moveToNext())
                }
            }
            emailList.toList()
        } catch (e: SecurityException) {
            Log.e(TAG, "Calendar permission denied", e)
            emptyList()
        }
    }

    fun getCalendarsForEmail(contentResolver: ContentResolver, email: String): List<CalendarInfo> {
        return try {

            val calendarList = mutableListOf<CalendarInfo>()

            val uri = CalendarContract.Calendars.CONTENT_URI
            val projection = arrayOf(
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.CALENDAR_COLOR,
                CalendarContract.Calendars.ACCOUNT_NAME
            )
            val selection = "${CalendarContract.Calendars.ACCOUNT_NAME} = ?"
            val selectionArgs = arrayOf(email)

            val cursor = contentResolver.query(uri, projection, selection, selectionArgs, null)

            cursor?.use {
                if (it.moveToFirst()) {
                    val idIndex = it.getColumnIndex(CalendarContract.Calendars._ID)
                    val nameIndex =
                        it.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)
                    val colorIndex = it.getColumnIndex(CalendarContract.Calendars.CALENDAR_COLOR)

                    do {
                        val id = it.getLong(idIndex)
                        val name = it.getString(nameIndex)
                        val color = it.getInt(colorIndex)
                        calendarList.add(CalendarInfo(id, name, color, email))
                    } while (it.moveToNext())
                }
            }

            return calendarList
        } catch (e: SecurityException) {
            Log.e(TAG, "Calendar permission denied", e)
            emptyList()
        }
    }

    fun getCalendarEvents(
        contentResolver: ContentResolver,
        selectedCalendars: Set<Long>,
        startTime: Long,
        endTime: Long
    ): List<CalendarEvent> {
        return try {
            val eventList = mutableListOf<CalendarEvent>()

            if (selectedCalendars.isEmpty()) return eventList

            // Create a map of calendar IDs to their names and colors
            val calendarMap = mutableMapOf<Long, Pair<String, Int>>()
            val calendarUri = CalendarContract.Calendars.CONTENT_URI
            val calendarProjection = arrayOf(
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.CALENDAR_COLOR
            )

            val calendarCursor = contentResolver.query(
                calendarUri,
                calendarProjection,
                null,
                null,
                null
            )

            calendarCursor?.use {
                if (it.moveToFirst()) {
                    val idIndex = it.getColumnIndex(CalendarContract.Calendars._ID)
                    val nameIndex =
                        it.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)
                    val colorIndex = it.getColumnIndex(CalendarContract.Calendars.CALENDAR_COLOR)

                    do {
                        val id = it.getLong(idIndex)
                        val name = it.getString(nameIndex)
                        val color = it.getInt(colorIndex)
                        calendarMap[id] = name to color
                    } while (it.moveToNext())
                }
            }

            val uri = CalendarContract.Events.CONTENT_URI
            val projection = arrayOf(
                CalendarContract.Events._ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.EVENT_LOCATION,
                CalendarContract.Events.CALENDAR_ID,
                CalendarContract.Events.ALL_DAY // Ensure ALL_DAY is included
            )

            val calendarIdPlaceholders = selectedCalendars.joinToString(",") { "?" }

            // Modified selection query: This gets all events between startTime and endTime
            val selection = """
        ${CalendarContract.Events.CALENDAR_ID} IN ($calendarIdPlaceholders)
        AND ${CalendarContract.Events.DTSTART} >= ? 
        AND ${CalendarContract.Events.DTSTART} <= ?
    """.trimIndent()

            val selectionArgs = selectedCalendars.map { it.toString() } + listOf(
                startTime.toString(),
                endTime.toString()
            )

            val cursor = contentResolver.query(
                uri,
                projection,
                selection,
                selectionArgs.toTypedArray(),
                "${CalendarContract.Events.DTSTART} ASC"
            )

            cursor?.let {
                if (it.moveToFirst()) {
                    val titleIndex = it.getColumnIndex(CalendarContract.Events.TITLE)
                    val startIndex = it.getColumnIndex(CalendarContract.Events.DTSTART)
                    val endIndex = it.getColumnIndex(CalendarContract.Events.DTEND)
                    val descriptionIndex = it.getColumnIndex(CalendarContract.Events.DESCRIPTION)
                    val locationIndex = it.getColumnIndex(CalendarContract.Events.EVENT_LOCATION)
                    val calendarIdIndex = it.getColumnIndex(CalendarContract.Events.CALENDAR_ID)
                    val allDayIndex = it.getColumnIndex(CalendarContract.Events.ALL_DAY)

                    do {
                        val title = it.getString(titleIndex) ?: "No Title"
                        val startTime = it.getLong(startIndex)
                        val endTime = it.getLong(endIndex)
                        val description = it.getString(descriptionIndex)
                        val location = it.getString(locationIndex)
                        val calendarId = it.getLong(calendarIdIndex)
                        val isAllDay = it.getInt(allDayIndex) == 1

                        // Skip all-day events based on the ALL_DAY flag
                        if (isAllDay) continue

                        // Convert start and end times to Calendar objects for time checks
                        val startCalendar =
                            Calendar.getInstance().apply { timeInMillis = startTime }
                        val endCalendar = Calendar.getInstance().apply { timeInMillis = endTime }

                        val startHour = startCalendar.get(Calendar.HOUR_OF_DAY)
                        val startMinute = startCalendar.get(Calendar.MINUTE)
                        val endHour = endCalendar.get(Calendar.HOUR_OF_DAY)
                        val endMinute = endCalendar.get(Calendar.MINUTE)

                        // Ensure we are not mistakenly filtering out events that start at 12:00 AM
                        val isValidTimedEvent =
                            !(startHour == 0 && startMinute == 0 && endHour == 0 && endMinute == 0)

                        if (!isValidTimedEvent) continue // Skip full-day events with start and end at midnight

                        val startDate = Date(startTime)
                        val endDate = Date(endTime)
                        val dateFormatter =
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

                        val (calendarName, calendarColor) = calendarMap[calendarId]
                            ?: ("Unknown" to 0)

                        eventList.add(
                            CalendarEvent(
                                title = title,
                                calendarName = calendarName,
                                startTime = dateFormatter.format(startDate),
                                endTime = dateFormatter.format(endDate),
                                description = description,
                                location = location,
                                color = calendarColor
                            )
                        )
                    } while (it.moveToNext())
                }
                it.close()
            } ?: run {
                Log.e(TAG, "Failed to fetch events. Cursor is null.")
            }

            return eventList
        } catch (e: SecurityException) {
            Log.e(TAG, "Calendar permission denied", e)
            emptyList()
        }
    }
}

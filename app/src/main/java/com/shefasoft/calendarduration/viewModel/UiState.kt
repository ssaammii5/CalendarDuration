package com.shefasoft.calendarduration.viewModel

import com.shefasoft.calendarduration.model.CalendarEvent
import com.shefasoft.calendarduration.model.CalendarInfo


sealed class UiState {
    object Loading : UiState()
    data class ShowEmailSelection(val emails: List<String>) : UiState()
    data class ShowCalendarSelection(
        val calendars: List<CalendarInfo>,
        val selectedCalendars: Set<Long> = emptySet()
    ) : UiState()
    data class ShowEventsList(val events: List<CalendarEvent>) : UiState()
    data class ShowError(val title: String, val message: String) : UiState()
}

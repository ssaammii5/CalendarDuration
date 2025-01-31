package com.shefasoft.calendarduration.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shefasoft.calendarduration.repository.CalendarRepository
import kotlinx.coroutines.launch
import java.util.Calendar

class MainViewModel(private val repository: CalendarRepository) : ViewModel() {

    val uiState = mutableStateOf<UiState>(UiState.Loading)
    private val _selectedDate = mutableStateOf(Calendar.getInstance())
    val selectedDate = _selectedDate

    init {
        viewModelScope.launch {
            checkAndProceed()
        }
    }

    fun updateSelectedDate(calendar: Calendar) {
        _selectedDate.value = calendar
    }

    private suspend fun checkAndProceed() {
        try {
            val savedEmail = repository.getSelectedEmail()
            val savedCalendars = repository.getSelectedCalendars()

            if (savedEmail != null && savedCalendars.isNotEmpty()) {
                loadCalendarEvents()
            } else {
                val emails = repository.getAvailableEmails()
                if (emails.isNotEmpty()) {
                    uiState.value = UiState.ShowEmailSelection(emails)
                } else {
                    uiState.value = UiState.ShowError(
                        title = "Permission Required",
                        message = "Calendar access is needed to view events."
                    )
                }
            }
        } catch (e: Exception) {
            uiState.value = UiState.ShowError(
                title = "Error",
                message = "Failed to access calendar data: ${e.localizedMessage}"
            )
        }
    }

    private suspend fun checkCalendarPermission() {
        // This function will be triggered externally based on permission result
        // Placeholder if needed
    }

    fun handlePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            viewModelScope.launch {
                checkAndProceed()
            }
        } else {
            uiState.value = UiState.ShowError(
                title = "Permission Denied",
                message = "Calendar permission is required to proceed."
            )
        }
    }

    private suspend fun proceedAfterPermission() {
        val emails = repository.getAvailableEmails()
        if (emails.isNotEmpty()) {
            uiState.value = UiState.ShowEmailSelection(emails)
        } else {
            uiState.value = UiState.ShowError(
                title = "No Emails Found",
                message = "No calendar accounts found on this device."
            )
        }
    }

    fun selectEmail(email: String) {
        viewModelScope.launch {
            repository.clearSelectedEmail()
            repository.insertSelectedEmail(email)
            val calendars = repository.getCalendarsForEmail(email)
            if (calendars.isNotEmpty()) {
                uiState.value = UiState.ShowCalendarSelection(calendars)
            } else {
                uiState.value = UiState.ShowError(
                    title = "No Calendars Found",
                    message = "No calendars found for the selected email."
                )
            }
        }
    }

    fun selectCalendars(selectedCalendarIds: Set<Long>) {
        viewModelScope.launch {
            repository.clearSelectedCalendars()
            repository.insertSelectedCalendars(selectedCalendarIds.toList())
            loadCalendarEvents() // Initial load with current date
        }
    }

    fun loadCalendarEvents() {
        viewModelScope.launch {
            _loadCalendarEvents()
        }
    }

    private suspend fun _loadCalendarEvents() {
        val selectedCalendars = repository.getSelectedCalendars().toSet()
        if (selectedCalendars.isEmpty()) return

        // Get start/end of currently selected date
        val calendar = selectedDate.value.clone() as Calendar
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis

        val events = repository.getCalendarEvents(selectedCalendars, startOfDay, endOfDay)
        uiState.value = UiState.ShowEventsList(events)
    }


    fun reselect() {
        viewModelScope.launch {
            repository.clearSelectedEmail()
            repository.clearSelectedCalendars()
            uiState.value = UiState.Loading
            proceedAfterPermission()
        }
    }
}

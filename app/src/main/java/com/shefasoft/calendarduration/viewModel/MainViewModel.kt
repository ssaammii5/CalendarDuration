package com.shefasoft.calendarduration.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shefasoft.calendarduration.repository.CalendarRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CalendarRepository) : ViewModel() {

    val uiState = mutableStateOf<UiState>(UiState.Loading)

    init {
        viewModelScope.launch {
            checkAndProceed()
        }
    }

    private suspend fun checkAndProceed() {
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
                    title = "No Emails Found",
                    message = "No calendar accounts found on this device."
                )
            }
        }
    }

    private suspend fun checkCalendarPermission() {
        // This function will be triggered externally based on permission result
        // Placeholder if needed
    }

    fun handlePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            viewModelScope.launch {
                proceedAfterPermission()
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
            loadCalendarEvents()
        }
    }

    private suspend fun loadCalendarEvents() {
        val selectedCalendars = repository.getSelectedCalendars().toSet()
        if (selectedCalendars.isEmpty()) return

        val events = repository.getCalendarEvents(selectedCalendars)
        if (uiState.value !is UiState.ShowEventsList) {
            uiState.value = UiState.ShowEventsList(events)
        }
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

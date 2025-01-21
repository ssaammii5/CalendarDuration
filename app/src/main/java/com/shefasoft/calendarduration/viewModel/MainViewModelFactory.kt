package com.shefasoft.calendarduration.viewModel

import android.content.ContentResolver
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shefasoft.calendarduration.data.local.AppDatabase
import com.shefasoft.calendarduration.repository.CalendarRepository


class MainViewModelFactory(private val context: Context, private val contentResolver: ContentResolver) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val database = AppDatabase.getDatabase(context)
            val repository = CalendarRepository(database.calendarDao(), contentResolver)
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

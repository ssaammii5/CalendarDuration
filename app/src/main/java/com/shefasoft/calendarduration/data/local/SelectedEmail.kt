package com.shefasoft.calendarduration.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_email")
data class SelectedEmail(
    @PrimaryKey val email: String
)

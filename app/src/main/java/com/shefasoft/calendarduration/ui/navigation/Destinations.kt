package com.shefasoft.calendarduration.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destinations{
    @Serializable
    object HomeScreen
    @Serializable
    object EventDetailScreen
    @Serializable
    object LoginScreen
    @Serializable
    object SettingsScreen
}
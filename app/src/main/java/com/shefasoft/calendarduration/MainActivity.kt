package com.shefasoft.calendarduration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shefasoft.calendarduration.ui.navigation.Destinations
import com.shefasoft.calendarduration.ui.screens.EventDetailsScreen
import com.shefasoft.calendarduration.ui.screens.HomeScreen
import com.shefasoft.calendarduration.ui.screens.LoginScreen
import com.shefasoft.calendarduration.ui.screens.SettingsScreen
import com.shefasoft.calendarduration.ui.theme.CalendarDurationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            CalendarDurationTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Destinations.LoginScreen
                ){
                    composable<Destinations.LoginScreen>{
                        LoginScreen()
                    }
                    composable<Destinations.HomeScreen>{
                        HomeScreen()
                    }
                    composable<Destinations.EventDetailScreen>{
                        EventDetailsScreen()
                    }
                    composable<Destinations.SettingsScreen>{
                        SettingsScreen()
                    }
                }
            }
        }
    }
}

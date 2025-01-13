package com.shefasoft.calendarduration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shefasoft.calendarduration.ui.components.TopAppBarMenu
import com.shefasoft.calendarduration.ui.navigation.Destinations
import com.shefasoft.calendarduration.ui.screens.EventDetailsScreen
import com.shefasoft.calendarduration.ui.screens.HomeScreen
import com.shefasoft.calendarduration.ui.screens.LoginScreen
import com.shefasoft.calendarduration.ui.screens.SettingsScreen
import com.shefasoft.calendarduration.ui.theme.CalendarDurationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            CalendarDurationTheme {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination
                val scrollBehavior =
                    TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

                Scaffold(
                    topBar = {
                        TopAppBarMenu(navController)
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.HomeScreen
                    ) {
                        composable<Destinations.LoginScreen> {
                            LoginScreen()
                        }
                        composable<Destinations.HomeScreen> {
                            HomeScreen(
                                modifier = Modifier
                                    .padding(innerPadding)
                            )
                        }
                        composable<Destinations.EventDetailScreen> {
                            EventDetailsScreen(
                                modifier = Modifier
                                    .padding(innerPadding)
                            )
                        }
                        composable<Destinations.SettingsScreen> {
                            SettingsScreen(
                                modifier = Modifier
                                    .padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

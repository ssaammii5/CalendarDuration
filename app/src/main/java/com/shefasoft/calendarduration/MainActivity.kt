package com.shefasoft.calendarduration

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shefasoft.calendarduration.ui.components.TopAppBarMenu
import com.shefasoft.calendarduration.ui.components.localCal.CalendarSelectionScreen
import com.shefasoft.calendarduration.ui.components.localCal.EmailSelectionScreen
import com.shefasoft.calendarduration.ui.components.localCal.ErrorDialog
import com.shefasoft.calendarduration.ui.components.localCal.LoadingScreen
import com.shefasoft.calendarduration.ui.navigation.Destinations
import com.shefasoft.calendarduration.ui.screens.*
import com.shefasoft.calendarduration.ui.theme.CalendarDurationTheme
import com.shefasoft.calendarduration.viewModel.MainViewModel
import com.shefasoft.calendarduration.viewModel.MainViewModelFactory
import com.shefasoft.calendarduration.viewModel.UiState


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = MainViewModelFactory(this, contentResolver)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        setContent {
            CalendarDurationTheme {
                //StatusBar fixing
                val isDarkTheme = isSystemInDarkTheme()
                val context = LocalContext.current
                val window = (context as? Activity)?.window

                LaunchedEffect(isDarkTheme) {
                    window?.let {
                        WindowCompat.setDecorFitsSystemWindows(it, false)
                        val windowInsetsController = WindowInsetsControllerCompat(it, it.decorView)

                        // Apply correct status bar text/icon color
                        windowInsetsController.isAppearanceLightStatusBars = !isDarkTheme
                    }
                }


                // Observe your ViewModel’s UiState. Adjust as needed:
                val uiState = viewModel.uiState.value

                // Permission launcher
                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        viewModel.handlePermissionResult(isGranted)
                    }
                )

                // Check and request permission on first composition
                LaunchedEffect(Unit) {
                    when {
                        ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.READ_CALENDAR
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            viewModel.handlePermissionResult(true)
                        }
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            this@MainActivity,
                            Manifest.permission.READ_CALENDAR
                        ) -> {
                            // Show custom rationale UI if needed
                        }
                        else -> {
                            permissionLauncher.launch(Manifest.permission.READ_CALENDAR)
                        }
                    }
                }

                // Depending on the UiState, either show your selection flows or show the main NavHost
                when (uiState) {
                    is UiState.Loading -> {
                        LoadingScreen()
                    }
                    is UiState.ShowEmailSelection -> {
                        EmailSelectionScreen(
                            emails = uiState.emails,
                            onEmailSelected = { email ->
                                viewModel.selectEmail(email)
                            }
                        )
                    }
                    is UiState.ShowCalendarSelection -> {
                        CalendarSelectionScreen(
                            calendars = uiState.calendars,
                            onSelectionChange = { selectedIds ->
                                // Optionally handle selection changes
                            },
                            onImport = { selectedIds ->
                                viewModel.selectCalendars(selectedIds)
                            }
                        )
                    }
                    is UiState.ShowEventsList -> {
                        MainNavFlow(viewModel = viewModel)
                    }
                    is UiState.ShowError -> {
                        ErrorDialog(
                            title = uiState.title,
                            message = uiState.message,
                            onDismiss = {
                                if (uiState is UiState.ShowError && uiState.title == "Permission Denied") {
                                    finish() // Close app if permission is critical
                                } else {
                                    viewModel.reselect()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavFlow(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

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
                    viewModel = viewModel,
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
            composable<Destinations.EventDetailScreen> {
                EventDetailsScreen(
                    viewModel = viewModel,
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
            composable<Destinations.SettingsScreen> {
                SettingsScreen(
                    viewModel = viewModel,
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
        }
    }
}

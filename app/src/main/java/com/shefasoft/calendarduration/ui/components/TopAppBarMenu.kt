package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.shefasoft.calendarduration.R
import com.shefasoft.calendarduration.ui.navigation.Destinations

// import androidx.navigation.NavDestination.Companion.hasRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMenu(
    navController: NavController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val isSelectedHome = backStackEntry?.destination?.hasRoute<Destinations.HomeScreen>() == true
    val isSelectedEvent = backStackEntry?.destination?.hasRoute<Destinations.EventDetailScreen>() == true
    val isSelectedSettings =
        backStackEntry?.destination?.hasRoute<Destinations.SettingsScreen>() == true


    TopAppBar(
        title = {
            Text(
                text = "Calendar Duration"
            )
        },
        actions = {
            IconButtonWithHighlights(
                icon = R.drawable.ic_calendars,
                isSelected = isSelectedHome,
                onClick = {
                    if (!isSelectedHome) {
                        navController.navigate(Destinations.HomeScreen) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
            IconButtonWithHighlights(
                icon = R.drawable.ic_eventduration,
                isSelected = isSelectedEvent,
                onClick = {
                    if (!isSelectedEvent) {
                        navController.navigate(Destinations.EventDetailScreen) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
            IconButtonWithHighlights(
                icon = R.drawable.ic_settings,
                isSelected = isSelectedSettings,
                onClick = {
                    if (!isSelectedSettings) {
                        navController.navigate(Destinations.SettingsScreen) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    )
}


@Composable
fun IconButtonWithHighlights(icon: Int, isSelected: Boolean, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color
                    .Transparent,
                shape = RoundedCornerShape(40)
            )
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme
                .onSurface
        )
    }
}
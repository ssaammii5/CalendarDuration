package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.shefasoft.calendarduration.R
import com.shefasoft.calendarduration.ui.navigation.Destinations

@Composable
fun TopAppBarComponent(
    navController: NavController,
    isSelected: Boolean,
    headingText: String
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val destRoutes = listOf(
        Destinations.HomeScreen,
        Destinations.EventDetailScreen,
        Destinations.SettingsScreen
    )
    val isSelected = currentRoute?.hierarchy?.any { it == Destinations.SettingsScreen } == true
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = headingText,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButtonWithHighlight(
                icon = R.drawable.ic_calendars,
                isSelected = isSelected,
                onClick = {
                    if (!isSelected) {
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
            IconButtonWithHighlight(
                icon = R.drawable.ic_clock_duration,
                isSelected = isSelected,
                onClick = {
                    if (!isSelected) {
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
            IconButtonWithHighlight(
                icon = R.drawable.ic_settings,
                //isSelected = currentRoute?.hierarchy?.any { it == Destinations.SettingsScreen } == true,
                isSelected = isSelected,
                onClick = {
                    if (!isSelected) {
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
    }
}

@Composable
fun IconButtonWithHighlight(icon: Int, isSelected: Boolean, onClick: () -> Unit) {
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

//@Preview(showBackground = true)
//@Composable
//fun PreviewTopAppBarComponent() {
//    TopAppBarComponent(navController)
//}

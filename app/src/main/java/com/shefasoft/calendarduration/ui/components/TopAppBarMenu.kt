package com.shefasoft.calendarduration.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shefasoft.calendarduration.R
import com.shefasoft.calendarduration.ui.navigation.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMenu(
    navController: NavController,
    isSelected: Boolean,
){
    TopAppBar(
        title ={
            Text(
                text = "Calendar Duration"
            )
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Destinations.HomeScreen) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_calendars),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme
                        .onSurface
                )
            }
            IconButton(onClick = {
                navController.navigate(Destinations.EventDetailScreen) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_eventduration),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme
                        .onSurface
                )
            }
            IconButton(onClick = {
                navController.navigate(Destinations.SettingsScreen) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme
                        .onSurface
                )
            }
        }
    )
}
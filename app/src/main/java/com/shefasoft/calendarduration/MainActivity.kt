package com.shefasoft.calendarduration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shefasoft.calendarduration.ui.screens.HomeScreen
import com.shefasoft.calendarduration.ui.theme.CalendarDurationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            CalendarDurationTheme {
                HomeScreen()
            }
        }
    }
}

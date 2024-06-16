package com.example.emailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emailapp.ui.theme.EmailAppTheme
import com.example.emailapp.view.screens.calendar.CalendarScreen
import com.example.emailapp.view.screens.emaillist.EmailListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailAppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "email_list") {
        composable("email_list") { EmailListScreen(navController) }
        composable("calendar") { CalendarScreen(navController) }
    }
}

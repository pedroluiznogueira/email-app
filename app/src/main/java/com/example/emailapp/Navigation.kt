package com.example.emailapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "emailList") {
        composable("emailList") { EmailListScreen(navController) }
        composable("calendar") { CalendarScreen(navController) }
    }
}

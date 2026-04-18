package com.miranda.eventosingsoft.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.miranda.eventosingsoft.ui.screens.detail.DetailScreen
import com.miranda.eventosingsoft.ui.screens.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDetail = {
                    navController.navigate(Screen.Detail.route)
                }
            )
        }
        composable(Screen.Detail.route) {
            DetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
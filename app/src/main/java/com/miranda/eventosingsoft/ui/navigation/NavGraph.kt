package com.miranda.eventosingsoft.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.miranda.eventosingsoft.ui.screens.home.HomeScreen
import com.miranda.eventosingsoft.ui.screens.create.CrearEventoScreen
import com.miranda.eventosingsoft.ui.screens.detail.DetailScreen
import com.miranda.eventosingsoft.ui.screens.favorites.FavoritesScreen
sealed class Screen(val route: String) {
    object Menu : Screen("menu")
    object Create : Screen("create")
    object Detail : Screen("detail")
    object Favorites : Screen("favorites")
}
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable(Screen.Menu.route) {
            HomeScreen(
                onNavigateToCreate = { navController.navigate(Screen.Create.route) },
                onNavigateToDetail = { navController.navigate(Screen.Detail.route) },
                onNavigateToFavorites = { navController.navigate(Screen.Favorites.route) }
            )
        }
        composable(Screen.Create.route) {
            CrearEventoScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Detail.route) {
            DetailScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
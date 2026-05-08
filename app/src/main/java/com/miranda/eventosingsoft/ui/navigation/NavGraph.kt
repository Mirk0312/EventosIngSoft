package com.miranda.eventosingsoft.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.miranda.eventosingsoft.EventosApp
import com.miranda.eventosingsoft.ui.screens.home.HomeScreen
import com.miranda.eventosingsoft.ui.screens.home.HomeViewModel
import com.miranda.eventosingsoft.ui.screens.favorite.FavoritosScreen
import com.miranda.eventosingsoft.ui.screens.create.CrearEventoScreen
import com.miranda.eventosingsoft.ui.screens.create.CreateViewModel
import com.miranda.eventosingsoft.ui.screens.detail.DetailScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val contexto = LocalContext.current
            val app = contexto.applicationContext as EventosApp
            val viewModel = HomeViewModel(app.repository)

            HomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = { id -> navController.navigate("detail/$id") },
                onNavigateToCreate = { navController.navigate("create") },
                onNavigateToFavorites = { navController.navigate("favorites") }
            )
        }

        composable("favorites") {
            val contexto = LocalContext.current
            val app = contexto.applicationContext as EventosApp
            val viewModel = HomeViewModel(app.repository)

            FavoritosScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("create") {
            val contexto = LocalContext.current
            val app = contexto.applicationContext as EventosApp
            val createViewModel = CreateViewModel(app.repository)

            CrearEventoScreen(
                viewModel = createViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "detail/{eventoId}",
            arguments = listOf(navArgument("eventoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("eventoId") ?: 0
            DetailScreen(
                eventoId = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
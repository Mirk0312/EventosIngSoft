package com.miranda.eventosingsoft.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.miranda.eventosingsoft.ui.screens.detail.DetailViewModel
import com.miranda.eventosingsoft.ui.screens.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    val contexto = LocalContext.current
    val app = contexto.applicationContext as EventosApp

    val sharedHomeViewModel = remember { HomeViewModel(app.repository) }

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // ruta del Splash Screen
        composable("splash") {
            SplashScreen(onNavigateToHome = {
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }

        // ruta de Pantalla Principal
        composable("home") {
            HomeScreen(
                viewModel = sharedHomeViewModel,
                onNavigateToDetail = { id -> navController.navigate("detail/$id") },
                onNavigateToCreate = { navController.navigate("create") },
                onNavigateToFavorites = { navController.navigate("favorites") }
            )
        }

        // Ruta de Favoritos
        composable("favorites") {
            FavoritosScreen(
                viewModel = sharedHomeViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }

        // ruta de Crear Nuevos Eventos
        composable("create") {
            val createViewModel = remember { CreateViewModel(app.repository) }
            CrearEventoScreen(
                viewModel = createViewModel,
                onNavigateBack = { navController.popBackStack() },
                eventoId = null
            )
        }

        // ruta Detalles del Evento
        composable(
            route = "detail/{eventoId}",
            arguments = listOf(navArgument("eventoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("eventoId") ?: 0
            val detailViewModel = remember { DetailViewModel(app.repository) }

            DetailScreen(
                eventoId = id,
                viewModel = detailViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { eventoId -> navController.navigate("edit/$eventoId") }
            )
        }

        // ruta de Editar Evento
        composable(
            route = "edit/{eventoId}",
            arguments = listOf(navArgument("eventoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("eventoId") ?: 0
            val createViewModel = remember { CreateViewModel(app.repository) }

            CrearEventoScreen(
                viewModel = createViewModel,
                onNavigateBack = { navController.popBackStack() },
                eventoId = id
            )
        }
    }
}
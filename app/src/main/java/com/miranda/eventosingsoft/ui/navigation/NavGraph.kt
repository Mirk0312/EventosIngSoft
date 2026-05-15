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

    // Compartimos el HomeViewModel para que favoritos y home vean los mismos datos
    val sharedHomeViewModel = remember { HomeViewModel(app.repository) }

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        //seccion del splashScreen
        composable("splash") {
            SplashScreen(onNavigateToHome = {
                navController.navigate("home") {
                    // Evita que el usuario regrese al Splash con el botón de atrás
                    popUpTo("splash") { inclusive = true }
                }
            })
        }

        //seccion de la pantalla principal
        composable("home") {
            HomeScreen(
                viewModel = sharedHomeViewModel,
                onNavigateToDetail = { id -> navController.navigate("detail/$id") },
                // Enviamos 0 para indicar que es un nuevo evento
                onNavigateToCreate = { navController.navigate("create_edit/0") },
                onNavigateToFavorites = { navController.navigate("favorites") }
            )
        }

        //seccion de favoritos
        composable("favorites") {
            FavoritosScreen(
                viewModel = sharedHomeViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { id -> navController.navigate("detail/$id") }
            )
        }

        //seccion de detalles del evento
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
                // Navegamos a la ruta unificada de edición
                onNavigateToEdit = { eventoId -> navController.navigate("create_edit/$eventoId") }
            )
        }

        // seccion de crear y editar
        // Usamos una sola definición para evitar duplicar código de ViewModels
        composable(
            route = "create_edit/{eventoId}",
            arguments = listOf(
                navArgument("eventoId") {
                    type = NavType.IntType
                    defaultValue = 0 // 0 significa "Nuevo"
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("eventoId") ?: 0
            val createViewModel = remember { CreateViewModel(app.repository) }

            CrearEventoScreen(
                viewModel = createViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                // Si el id es 0, pasamos null para que la pantalla sepa que es nuevo
                eventoId = if (id == 0) null else id
            )
        }
    }
}
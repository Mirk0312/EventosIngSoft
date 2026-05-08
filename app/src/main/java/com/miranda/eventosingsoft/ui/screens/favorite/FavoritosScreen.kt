package com.miranda.eventosingsoft.ui.screens.favorite

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.miranda.eventosingsoft.ui.screens.home.HomeViewModel
import com.miranda.eventosingsoft.ui.screens.home.EventItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(viewModel: HomeViewModel, onNavigateBack: () -> Unit) {
    val lista by viewModel.listaEventos.collectAsState()
    val favoritos = lista.filter { it.esFavorito }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Favoritos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(favoritos) { evento ->
                EventItemCard(evento = evento, onClick = { }, onToggleFavorite = { viewModel.cambiarFavorito(evento) })
            }
        }
    }
}
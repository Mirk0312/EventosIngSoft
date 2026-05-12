package com.miranda.eventosingsoft.ui.screens.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape // Añadida
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip // Añadida
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Añadida para el diseño del título
import com.miranda.eventosingsoft.ui.screens.home.HomeViewModel
import com.miranda.eventosingsoft.ui.screens.home.EventItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(
    viewModel: HomeViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    val lista by viewModel.listaEventos.collectAsState()
    val favoritos = lista.filter { it.esFavorito }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "MIS FAVORITOS",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.5.sp,
                                color = Color(0xFF6750A4)
                            )
                        )
                        // Línea decorativa color Oro UES
                        Surface(
                            modifier = Modifier
                                .width(45.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = Color(0xFFFFC107)
                        ) {}
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        if (favoritos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aún no tienes eventos favoritos",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(items = favoritos, key = { it.id }) { evento ->
                        EventItemCard(
                            evento = evento,
                            onClick = { onNavigateToDetail(evento.id) },
                            onToggleFavorite = { viewModel.cambiarFavorito(evento) },
                            onDelete = { viewModel.borrarEvento(evento) }
                        )
                    }
                }
            }
        }
    }
}
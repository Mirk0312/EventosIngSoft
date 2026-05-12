package com.miranda.eventosingsoft.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Importación para el tamaño de letra
import coil.compose.AsyncImage
import com.miranda.eventosingsoft.data.local.EventoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToCreate: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToFavorites: () -> Unit
) {
    val listaDeEventos by viewModel.listaEventos.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "EVENTOS UES",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.5.sp,
                                color = Color(0xFF6750A4)
                            )
                        )

                        Surface(
                            modifier = Modifier
                                .width(45.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = Color(0xFFFFC107)
                        ) {}
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToFavorites) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Ver Favoritos",
                            tint = Color(0xFFFFC107)
                        )
                    }
                },
                // Color de fondo de la barra
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreate,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear", tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    ) { innerPadding ->
        if (listaDeEventos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.EventNote,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No hay eventos registrados",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        } else {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(500))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp) // Espacio para el FAB
                ) {
                    items(
                        items = listaDeEventos,
                        key = { it.id }
                    ) { evento ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventItemCard(
    evento: EventoEntity,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // esto es de la imagen o icono
            if (evento.imagenUri != null && evento.imagenUri!!.isNotEmpty()) {
                AsyncImage(
                    model = evento.imagenUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp)), // Bordes más redondos
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Event,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = evento.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1 // Para que no se amontone el texto
                )
                Text(
                    text = evento.fecha,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Row {
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (evento.esFavorito) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = null,
                        tint = if (evento.esFavorito) Color(0xFFFFC107) else Color.Gray
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Borrar",
                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}
package com.miranda.eventosingsoft.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista De Eventos", color = Color(0xFF6750A4)) },
                actions = {
                    IconButton(onClick = onNavigateToFavorites) {
                        Icon(Icons.Default.StarBorder, null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Crear")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaDeEventos) { evento ->
                EventItemCard(
                    evento = evento,
                    onClick = { onNavigateToDetail(evento.id) },
                    onToggleFavorite = { viewModel.cambiarFavorito(evento) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventItemCard(evento: EventoEntity, onClick: () -> Unit, onToggleFavorite: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (evento.esFavorito) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = null,
                    tint = if (evento.esFavorito) Color(0xFFFFC107) else Color.Gray
                )
            }
            Column {
                Text(text = evento.titulo, style = MaterialTheme.typography.titleMedium)
                Text(evento.fecha, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
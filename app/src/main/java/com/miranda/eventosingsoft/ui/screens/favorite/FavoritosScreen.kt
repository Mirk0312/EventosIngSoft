package com.miranda.eventosingsoft.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Eventos Favoritos",
                            color = Color(0xFF6750A4),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Text("Ingeniería en Software", style = MaterialTheme.typography.bodySmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    // Icono de la estrella rellena (amarilla) según tu diseño
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Aquí mostramos solo 2 ejemplos de favoritos como en tu imagen
            items(2) {
                FavoriteEventCard()
            }
        }
    }
}

@Composable
fun FavoriteEventCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3))
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                // Placeholder de imagen
                Surface(
                    modifier = Modifier.size(80.dp),
                    color = Color.LightGray,
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.padding(20.dp))
                }
                // Estrella de favorito encima de la foto (como en el dibujo)
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(20.dp).align(Alignment.TopStart)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    color = Color(0xFF5D5FEF),
                    modifier = Modifier.fillMaxWidth().height(8.dp),
                    shape = MaterialTheme.shapes.extraSmall
                ) {}
                Spacer(modifier = Modifier.height(8.dp))
                Text("☆☆☆☆☆", color = Color(0xFF5D5FEF))
                Text("00/12/0000", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
package com.miranda.eventosingsoft.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetail: () -> Unit // Función que se dispara al pulsar el botón
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Menu") }) // Barra superior con título
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // Respeta el espacio de la TopAppBar
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Mi pantalla principal")
                Button(onClick = onNavigateToDetail) { // Al hacer clic, navega a detalles
                    Text("Ir a Detalles")
                }
            }
        }
    }
}
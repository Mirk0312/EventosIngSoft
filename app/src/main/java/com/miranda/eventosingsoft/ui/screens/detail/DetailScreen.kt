package com.miranda.eventosingsoft.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miranda.eventosingsoft.R // Asegúrate de tener una imagen en res/drawable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Torneo De Ajedrez", color = Color(0xFF6750A4), fontWeight = FontWeight.Bold)
                        Text("Ingeniería En Software", style = MaterialTheme.typography.bodySmall)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen del evento (placeholder)
            Surface(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                shape = MaterialTheme.shapes.medium,
                color = Color.LightGray
            ) {
                // Aquí iría tu imagen real cuando la tengas en drawable
                Box(contentAlignment = Alignment.Center) {
                    Text("Imagen del Evento")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("00/12/0000", modifier = Modifier.align(Alignment.End), color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción detallada
            Text(
                text = "El Torneo de Ajedrez UES será un evento académico y deportivo diseñado para fomentar la sana competencia entre estudiantes de la Universidad de Sonora...",
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de regreso inferior centrado (igual que en tu dibujo)
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.size(60.dp)
            ) {
                Surface(
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = Color(0xFF7B61FF)
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.White,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}
package com.miranda.eventosingsoft.ui.screens.create
import com.miranda.eventosingsoft.ui.screens.create.CreateViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearEventoScreen(
    // Asegúrate de que tu inyección de dependencias o Factory esté configurada
    // Si te marca error aquí, es normal hasta que configuremos el ViewModelFactory
    viewModel: CreateViewModel,
    onNavigateBack: () -> Unit

) {
    // Variables de estado para capturar lo que el usuario escribe
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Nuevo Evento") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo para el Título
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título del Evento") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo para la Descripción
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Campo para la Fecha
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha (ej. 12 de Octubre)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo para la Ubicación
            OutlinedTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it },
                label = { Text("Ubicación / Lugar") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón de Guardar
            Button(
                onClick = {
                    if (titulo.isNotBlank()) {
                        viewModel.guardarNuevoEvento(titulo, descripcion, fecha, ubicacion)
                        onNavigateBack() // Regresa al Home tras guardar
                    }
                }
            ) { Text("Guardar Evento") }
        }
    }
}


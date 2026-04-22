package com.miranda.eventosingsoft.ui.screens.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearEventoScreen(onNavigateBack: () -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Creacion Del Evento", color = Color(0xFF6750A4), fontWeight = FontWeight.Bold) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 32.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField("Titulo", titulo) { titulo = it }
            CustomTextField("Descripcion", descripcion) { descripcion = it }
            CustomTextField("Categoria", categoria) { categoria = it }
            CustomTextField("Ubicacion", ubicacion) { ubicacion = it }

            Text("Fecha", fontWeight = FontWeight.Medium)
            Text("00/12/0000", color = Color.Gray)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                IconButton(onClick = onNavigateBack, modifier = Modifier.size(60.dp)) {
                    Surface(shape = androidx.compose.foundation.shape.CircleShape, color = Color(0xFF7B61FF)) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White, modifier = Modifier.padding(12.dp))
                    }
                }
                IconButton(onClick = { }, modifier = Modifier.size(60.dp)) {
                    Surface(shape = androidx.compose.foundation.shape.CircleShape, color = Color(0xFF27AE60)) {
                        Icon(Icons.Default.Add, null, tint = Color.White, modifier = Modifier.padding(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 18.sp)
        Surface(color = Color(0xFFEEEEEE), modifier = Modifier.fillMaxWidth().height(45.dp), shape = MaterialTheme.shapes.small) {
            TextField(value = value, onValueChange = onValueChange, singleLine = true, colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent))
        }
    }
}
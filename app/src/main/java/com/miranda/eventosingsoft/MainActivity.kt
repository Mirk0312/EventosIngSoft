package com.miranda.eventosingsoft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.miranda.eventosingsoft.ui.navigation.NavGraph
import com.miranda.eventosingsoft.ui.theme.EventosIngSoftTheme // Asegúrate que el nombre coincida con tu tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            // Nombre de tu tema generado al crear el proyecto
            EventosIngSoftTheme {
                val navController = rememberNavController()

                // El NavGraph gestiona el contenido de la pantalla
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    NavGraph(navController = navController)
                }
            }
        }
    }
}
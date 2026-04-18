package com.miranda.eventosingsoft.ui.screens.home

import androidx.lifecycle.ViewModel
import com.miranda.eventosingsoft.data.repository.MainRepository

//ViewModel para la pantalla principal (Home).
//Se encarga de gestionar los datos de la lista de elementos para la UI

class HomeViewModel(private val repository: MainRepository) : ViewModel() {
    // Aquí se gestionará la lógica del ViewModel para el Home
    // Por ejemplo, exponer el flujo de datos del repositorio a la pantalla
}
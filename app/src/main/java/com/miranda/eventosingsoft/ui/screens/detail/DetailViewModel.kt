// DetailViewModel.kt
package com.miranda.eventosingsoft.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.miranda.eventosingsoft.data.repository.MainRepository

class DetailViewModel(private val repository: MainRepository) : ViewModel() {
    // Esta función conecta con el repositorio
    fun obtenerEventoPorId(id: Int) = repository.obtenerEventoPorId(id)
}
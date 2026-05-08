package com.miranda.eventosingsoft.ui.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miranda.eventosingsoft.data.repository.MainRepository
import com.miranda.eventosingsoft.data.local.EventoEntity
import kotlinx.coroutines.launch

class CreateViewModel(private val repository: MainRepository) : ViewModel() {

    fun guardarNuevoEvento(
        titulo: String,
        descripcion: String,
        fecha: String,
        ubicacion: String
    ) {
        viewModelScope.launch {
            val nuevoEvento = EventoEntity(
                titulo = titulo,
                descripcion = descripcion,
                fecha = fecha,
                ubicacion = ubicacion,
                esFavorito = false
            )
            repository.insertarEvento(nuevoEvento)
        }
    }
}
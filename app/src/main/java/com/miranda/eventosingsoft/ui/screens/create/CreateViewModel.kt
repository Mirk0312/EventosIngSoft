package com.miranda.eventosingsoft.ui.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miranda.eventosingsoft.data.repository.MainRepository
import com.miranda.eventosingsoft.data.local.EventoEntity
import kotlinx.coroutines.launch

class CreateViewModel(private val repository: MainRepository) : ViewModel() {

    // Modificamos la función para que acepte un ID (por si editamos)
    // y la imagenUri (para la foto)
    fun guardarEvento(
        id: Int = 0, // Si es 0 es nuevo, si es diferente es edición
        titulo: String,
        descripcion: String,
        fecha: String,
        ubicacion: String,
        imagenUri: String? = null
    ) {
        viewModelScope.launch {
            val evento = EventoEntity(
                id = id, // Room usa este ID para decidir si inserta o actualiza
                titulo = titulo,
                descripcion = descripcion,
                fecha = fecha,
                ubicacion = ubicacion,
                imagenUri = imagenUri, // Guardamos lo que es la ruta de la foto
                esFavorito = false
            )

            if (id == 0) {
                repository.insertarEvento(evento)
            } else {
                repository.actualizarEvento(evento)
            }
        }
    }
    fun eliminarEvento(evento: EventoEntity) {
        viewModelScope.launch {
            repository.eliminarEvento(evento)
        }
    }
    fun obtenerEventoPorId(id: Int) = repository.obtenerEventoPorId(id)
}
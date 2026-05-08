package com.miranda.eventosingsoft.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miranda.eventosingsoft.data.repository.MainRepository
import com.miranda.eventosingsoft.data.local.EventoEntity
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MainRepository) : ViewModel() {
    val listaEventos: StateFlow<List<EventoEntity>> = repository.listaEventos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun cambiarFavorito(evento: EventoEntity) {
        viewModelScope.launch {
            repository.cambiarFavorito(evento)
        }
    }
}
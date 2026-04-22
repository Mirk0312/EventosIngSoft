package com.miranda.eventosingsoft.data.repository

import com.miranda.eventosingsoft.data.local.EventoDao
import com.miranda.eventosingsoft.data.local.EventoEntity
import kotlinx.coroutines.flow.Flow

class MainRepository(private val eventoDao: EventoDao) {
    // Esto conecta la base de datos con el ViewModel
    val allEvents: Flow<List<EventoEntity>> = eventoDao.getAllEvents()

    suspend fun insert(evento: EventoEntity) {
        eventoDao.insertEvento(evento)
    }
}
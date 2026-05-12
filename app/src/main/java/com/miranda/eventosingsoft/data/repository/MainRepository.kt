package com.miranda.eventosingsoft.data.repository

import com.miranda.eventosingsoft.data.local.EventoDao
import com.miranda.eventosingsoft.data.local.EventoEntity
import kotlinx.coroutines.flow.Flow

class MainRepository(private val dao: EventoDao) {
    val listaEventos: Flow<List<EventoEntity>> = dao.obtenerEventos()

    suspend fun insertarEvento(evento: EventoEntity) {
        dao.insertarEvento(evento)
    }

    suspend fun actualizarEvento(evento: EventoEntity) = dao.actualizarEvento(evento)

    suspend fun eliminarEvento(evento: EventoEntity) = dao.eliminarEvento(evento)

    suspend fun cambiarFavorito(evento: EventoEntity) {
        dao.actualizarEvento(evento.copy(esFavorito = !evento.esFavorito))
    }
    fun obtenerEventoPorId(id: Int): Flow<EventoEntity> {
            return dao.obtenerEventoPorId(id)
    }



}
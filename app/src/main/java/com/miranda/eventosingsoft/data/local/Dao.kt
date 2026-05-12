package com.miranda.eventosingsoft.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventoDao {
    @Query("SELECT * FROM eventos ORDER BY id DESC")
    fun obtenerEventos(): Flow<List<EventoEntity>>

    @Query("SELECT * FROM eventos WHERE id = :id")
    fun obtenerEventoPorId(id: Int): Flow<EventoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEvento(evento: EventoEntity)

    @Update
    suspend fun actualizarEvento(evento: EventoEntity)

    @Delete
    suspend fun eliminarEvento(evento: EventoEntity)
}